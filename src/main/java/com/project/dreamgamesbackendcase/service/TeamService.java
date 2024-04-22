package com.project.dreamgamesbackendcase.service;

import com.project.dreamgamesbackendcase.models.Team;
import com.project.dreamgamesbackendcase.models.User;
import com.project.dreamgamesbackendcase.repository.TeamRepository;
import com.project.dreamgamesbackendcase.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    // Creates a new team and deducts coins from the user's balance.
    @Transactional
    public Team createTeam(Long userId, String teamName) throws Exception {
        // Fetch user from the database or throw if not found
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        // Ensure the user has enough coins to create a team
        if (user.getCoins() < 1000) {
            throw new IllegalStateException("Insufficient balance to create a team");
        }

        // Initialize new team with zero members
        Team team = new Team();
        team.setName(teamName);
        team.setMemberCount(0);
        teamRepository.save(team);

        // Deduct coins and update user
        user.setCoins(user.getCoins() - 1000);
        userRepository.save(user);

        return team;
    }

    // Adds a user to a team if they have enough coins and the team has capacity.
    @Transactional
    public Team joinTeam(Long userId, Long teamId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        // Check user's balance before joining a team
        if (user.getCoins() < 1000) {
            throw new IllegalStateException("Insufficient balance to join the team");
        }

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalStateException("Team not found"));

        // Synchronize on the team object to handle concurrent join requests
        synchronized (team) {
            if (team.getMemberCount() >= team.getCapacity()) {
                throw new IllegalStateException("Team is at full capacity");
            }

            // Prevent user from joining the same team multiple times
            if (team.getMembers().contains(user)) {
                throw new IllegalStateException("User is already a member of this team");
            }

            // Add user to team and update both team and user states
            team.getMembers().add(user);
            team.setMemberCount(team.getMemberCount() + 1);
            teamRepository.save(team);

            // Deduct joining fee from user
            user.setCoins(user.getCoins() - 1000);
            userRepository.save(user);
        }

        return team;
    }

    // Removes a user from a team, adjusting the member count accordingly.
    @Transactional
    public void leaveTeam(Long userId, Long teamId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalStateException("Team not found"));

        synchronized (team) {
            if (team.getMemberCount() == 0) {
                throw new IllegalStateException("Team is empty");
            }

            // Ensure the user is currently a member of the team
            if (!team.getMembers().contains(user)) {
                throw new IllegalStateException("User is not a member of this team");
            }

            // Remove the user and update the team's member count
            team.removeMember(user);
            team.setMemberCount(team.getMemberCount() - 1);
            teamRepository.save(team);
        }
    }

    // Retrieves a list of teams with available spots.
    public List<Team> getAvailableTeams() {
        return teamRepository.findTeamsWithAvailableSpots();
    }
}
