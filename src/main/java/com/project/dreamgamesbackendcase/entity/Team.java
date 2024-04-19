package com.project.dreamgamesbackendcase.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "member_count")
    private int memberCount;
    private static final int CAPACITY = 20;

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
        return CAPACITY;
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
}
