package com.project.dreamgamesbackendcase.service;

import com.project.dreamgamesbackendcase.entity.Team;
import com.project.dreamgamesbackendcase.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public Team createTeam(String name) {
        if (teamRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Team already exists with this name");
        }
        Team newTeam = new Team();
        newTeam.setName(name);
        newTeam.setMemberCount(0);  // Initialize member count to zero
        return teamRepository.save(newTeam);
    }

    public Team joinTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        if (team.getMemberCount() >= team.getCapacity()) {
            throw new RuntimeException("Team is full");
        }
        team.setMemberCount(team.getMemberCount() + 1);
        return teamRepository.save(team);
    }

    public Team leaveTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        if (team.getMemberCount() == 0) {
            throw new RuntimeException("Team has no members");
        }
        team.setMemberCount(team.getMemberCount() - 1);
        return teamRepository.save(team);
    }
}
