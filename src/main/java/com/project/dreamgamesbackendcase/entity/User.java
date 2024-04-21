package com.project.dreamgamesbackendcase.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int level;
    private int coins;
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "team_members",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    @JsonIgnore
    private Set<Team> teams = new HashSet<>();

    // Constructors
    public User() {}

    public User(int level, int coins) {
        this.level = level;
        this.coins = coins;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public int getCoins() {
        return coins;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    // Helper methods to manage bidirectional relationship
    public void addTeam(Team team) {
        teams.add(team);
        team.getMembers().add(this);
    }

    public void removeTeam(Team team) {
        teams.remove(team);
        team.getMembers().remove(this);
    }
}
