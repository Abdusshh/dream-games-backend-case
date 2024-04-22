package com.project.dreamgamesbackendcase.controller;

import com.project.dreamgamesbackendcase.models.Team;
import com.project.dreamgamesbackendcase.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping("/add")
    public ResponseEntity<?> createTeam(@RequestParam Long userId, @RequestParam String teamName) {
        try {
            Team newTeam = teamService.createTeam(userId, teamName);
            return new ResponseEntity<>(newTeam, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/join")
    public ResponseEntity<?> joinTeam(@RequestParam Long userId, @RequestParam Long teamId) {
        try {
            Team team = teamService.joinTeam(userId, teamId);
            return new ResponseEntity<>(team, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/leave")
    public ResponseEntity<?> leaveTeam(@RequestParam Long userId, @RequestParam Long teamId) {
        try {
            teamService.leaveTeam(userId, teamId);
            return new ResponseEntity<>("Successfully left the team: " + teamId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-available")
    public ResponseEntity<List<Team>> getAvailableTeams() {
        List<Team> teams = teamService.getAvailableTeams();
        if (teams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }
}