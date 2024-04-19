package com.project.dreamgamesbackendcase.controller;

import com.project.dreamgamesbackendcase.entity.Team;
import com.project.dreamgamesbackendcase.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team newTeam = teamService.createTeam(team.getName());
        return new ResponseEntity<>(newTeam, HttpStatus.CREATED);
    }

    @PostMapping("/{teamId}/join")
    public ResponseEntity<Team> joinTeam(@PathVariable Long teamId) {
        Team team = teamService.joinTeam(teamId);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @PostMapping("/{teamId}/leave")
    public ResponseEntity<Team> leaveTeam(@PathVariable Long teamId) {
        Team team = teamService.leaveTeam(teamId);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }
}