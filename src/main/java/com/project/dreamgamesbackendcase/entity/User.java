package com.project.dreamgamesbackendcase.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int level;
    private int coins;

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
}
