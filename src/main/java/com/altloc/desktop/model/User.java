package com.altloc.desktop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Represents a user in the system with various attributes such as username,
 * email, score, level, and other account-related information.
 * This class is used to map user data, typically fetched from a backend
 * service, and includes fields for the user's personal data,
 * account details, and role-based access.
 */
public class User {
    private String id; // The unique identifier for the user
    private String username; // The username chosen by the user
    private String email; // The email address of the user
    private boolean emailVerified; // Flag indicating if the user's email is verified
    private String avatarKey; // Key or identifier for the user's avatar image
    private String role; // The role of the user (e.g., admin, user, etc.)
    private int score; // The user's current score in the system
    private int level; // The user's current level
    private long currency; // The user's current currency balance
    private long createdAt; // Timestamp representing when the user account was created

    @JsonProperty("isAdmin")
    private boolean isAdmin; // Flag indicating if the user has admin privileges

    /**
     * Default constructor for creating a new User instance.
     */
    public User() {
    }

    /**
     * Constructs a new User with the provided parameters.
     *
     * @param id            The unique identifier for the user
     * @param username      The username chosen by the user
     * @param email         The email address of the user
     * @param emailVerified Flag indicating if the user's email is verified
     * @param avatarKey     Key or identifier for the user's avatar image
     * @param role          The role of the user (e.g., admin, user)
     * @param score         The user's score in the system
     * @param level         The user's level
     * @param currency      The user's currency balance
     * @param createdAt     Timestamp representing when the account was created
     * @param isAdmin       Flag indicating if the user is an admin
     */
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

    // Getter and Setter methods for each field

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

    /**
     * Returns a string representation of the User object.
     * 
     * @return A string containing the user's information.
     */
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

    /**
     * Compares this User object with another object for equality.
     * Two User objects are considered equal if they have the same id, username,
     * email,
     * emailVerified status, avatarKey, role, score, level, currency, createdAt
     * timestamp, and isAdmin status.
     * 
     * @param o The object to compare this User to.
     * @return true if the objects are equal, false otherwise.
     */
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

    /**
     * Returns a hash code value for the User object.
     * The hash code is computed based on the user's id, username, email,
     * emailVerified status,
     * avatarKey, role, score, level, currency, createdAt timestamp, and isAdmin
     * status.
     * 
     * @return The hash code of the User object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, emailVerified, avatarKey, role, score, level, currency, createdAt,
                isAdmin);
    }
}
