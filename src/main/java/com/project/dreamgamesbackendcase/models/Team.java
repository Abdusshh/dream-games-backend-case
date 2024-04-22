package com.project.dreamgamesbackendcase.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teams")
public class Team {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "member_count", unique = true)
    private int memberCount;
    private int capacity = 20;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "team_members",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private Set<User> members = new HashSet<>();

    // Constructors
    public Team() {}

    public Team(String name) {
        this.name = name;
        this.memberCount = 0; // initially zero when team is created
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public void addMember(User user) {
        this.members.add(user);
        user.getTeams().add(this);
    }

    public void removeMember(User user) {
        this.members.remove(user);
        user.getTeams().remove(this);
    }
}
