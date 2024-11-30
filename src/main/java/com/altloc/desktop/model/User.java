package com.altloc.desktop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class User {
    private String id;
    private String username;
    private String email;
    private boolean emailVerified;
    private String avatarKey;
    private String role;
    private int score;
    private int level;
    private long currency;
    private long createdAt;

    @JsonProperty("isAdmin")
    private boolean isAdmin;

    // Конструкторы, геттеры и сеттеры

    public User() {
    }

    public User(String id, String username, String email, boolean emailVerified, String avatarKey,
            String role, int score, int level, long currency, long createdAt, boolean isAdmin) {
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

    public long getCurrency() {
        return currency;
    }

    public void setCurrency(long currency) {
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

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                ", avatarKey='" + avatarKey + '\'' +
                ", role='" + role + '\'' +
                ", score=" + score +
                ", level=" + level +
                ", currency=" + currency +
                ", createdAt=" + createdAt +
                ", isAdmin=" + isAdmin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return emailVerified == user.emailVerified &&
                score == user.score &&
                level == user.level &&
                currency == user.currency &&
                createdAt == user.createdAt &&
                isAdmin == user.isAdmin &&
                Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(avatarKey, user.avatarKey) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, emailVerified, avatarKey, role, score, level, currency, createdAt,
                isAdmin);
    }
}
