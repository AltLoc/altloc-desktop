package com.altloc.desktop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String id;
    private String username;
    private String email;
    private boolean emailVerified;
    private String avatarKey;
    private String role;
    private int score;
    private int level;
    private double currency;
    private long createdAt;

    @JsonProperty("isAdmin") // аннотация для маппинга поля JSON "isAdmin" на "isAdmin" в классе
    private boolean isAdmin; // Только для чтения, без сеттера

    // Конструкторы, геттеры и сеттеры

    public User() {
    }

    public User(String id, String username, String email, boolean emailVerified, String avatarKey,
            String role, int score, int level, double currency, long createdAt, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.emailVerified = emailVerified;
        this.avatarKey = avatarKey;
        this.role = role;
        this.score = score;
        this.level = level;
        this.currency = currency;
        this.createdAt = createdAt;
        this.isAdmin = isAdmin;
    }

    // Геттеры и сеттеры
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getAvatarKey() {
        return avatarKey;
    }

    public void setAvatarKey(String avatarKey) {
        this.avatarKey = avatarKey;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getCurrency() {
        return currency;
    }

    public void setCurrency(double currency) {
        this.currency = currency;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    // Сеттер для isAdmin удален, чтобы он не был изменяемым
}
