package com.project.dreamgamesbackendcase.service;

import com.project.dreamgamesbackendcase.entity.Team;
import com.project.dreamgamesbackendcase.entity.User;
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

    @Transactional
    public Team createTeam(Long userId, String teamName) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        if (user.getCoins() < 1000) {
            throw new IllegalStateException("Insufficient balance to create a team");
        }

        Team team = new Team();
        team.setName(teamName);
        team.setMemberCount(0); // Initial count is zero
        teamRepository.save(team);

        // Deduct coins and update user
        user.setCoins(user.getCoins() - 1000);
        userRepository.save(user);

        return team;
    }

    @Transactional
    public Team joinTeam(Long userId, Long teamId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        if (user.getCoins() < 1000) {
            throw new IllegalStateException("Insufficient balance to join the team");
        }

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalStateException("Team not found"));

        synchronized (team) {
            if (team.getMemberCount() >= team.getCapacity()) {
                throw new IllegalStateException("Team is at full capacity");
            }

            // Check if the user is already part of the team
            if (team.getMembers().contains(user)) {
                throw new IllegalStateException("User is already a member of this team");
            }

            team.getMembers().add(user);

            // Increment member count and update team
            team.setMemberCount(team.getMemberCount() + 1);
            teamRepository.save(team);

            // Deduct coins and update user
            user.setCoins(user.getCoins() - 1000);
            userRepository.save(user);
        }

        return team;
    }

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

            if (!team.getMembers().contains(user)) {
                throw new IllegalStateException("User is not a member of this team");
            }

            team.removeMember(user);

            // Decrement member count and update team
            team.setMemberCount(team.getMemberCount() - 1);
            teamRepository.save(team);
        }
    }

    public List<Team> getAvailableTeams() {
        return teamRepository.findTeamsWithAvailableSpots();
    }
}
