package com.project.dreamgamesbackendcase;

import com.project.dreamgamesbackendcase.entity.Team;
import com.project.dreamgamesbackendcase.entity.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DreamGamesBackendCaseApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void createUserTest() {
		ResponseEntity<User> response = restTemplate.postForEntity("/users/add", null, User.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody().getId());
		assertEquals(1, response.getBody().getLevel());
		assertEquals(5000, response.getBody().getCoins());
	}

	@Test
	public void updateUserTest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>("{}", headers);

		ResponseEntity<User> response = restTemplate.exchange(
				"/users/update-level/{userId}",
				HttpMethod.PUT,
				entity,
				User.class,
				1); // User with ID 1

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, response.getBody().getLevel()); // Level incremented by 1
	}

	@Test
	public void createTeamTest() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("userId", "1");
		params.add("teamName", "Savengers");

		ResponseEntity<Team> response = restTemplate.postForEntity("/teams/add", params, Team.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Savengers", response.getBody().getName());
		assertEquals(0, response.getBody().getMemberCount()); // Initially no members
	}

	@Test
	@Order(1)
	public void joinTeamTest() {
		// Setup
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("userId", "1");
		params.add("teamId", "2");

		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

		// Execute
		ResponseEntity<Team> response = restTemplate.exchange(
				"/teams/join?userId={userId}&teamId={teamId}",
				HttpMethod.PUT,
				requestEntity,
				Team.class,
				1, // userId
				2  // teamId
		);

		// Verify
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		Team team = response.getBody();
		assertEquals("Code Warriors", team.getName()); // Assuming this is the name of the team with ID 1
	}

	@Test
	@Order(2)
	public void leaveTeamTest() {
		// Assuming user 1 is already a member of team 2
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("userId", "1");
		params.add("teamId", "2");

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

		// Sending the request to leave the team
		ResponseEntity<String> response = restTemplate.exchange(
				"/teams/leave?userId={userId}&teamId={teamId}",
				HttpMethod.PUT,
				entity,
				String.class,
				1,  // userId
				2   // teamId
		);

		// Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("Successfully left the team: 2"));
	}

	@Test
	public void getAvailableTeamsTest() {
		ResponseEntity<Team[]> response = restTemplate.getForEntity("/teams/get-available", Team[].class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(3, response.getBody().length); // Initially inserted 2 teams
	}
}
