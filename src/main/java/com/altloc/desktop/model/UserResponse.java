package com.altloc.desktop.model;

/**
 * Represents a response object that contains a `User` object as its data.
 * This class is typically used to encapsulate the user data returned from an
 * API or service,
 * allowing for easy serialization and deserialization of the response.
 */
public class UserResponse {
    private User data; // The User data returned in the response

    /**
     * Gets the User data contained in the response.
     *
     * @return The User object representing the user data.
     */
    public User getData() {
        return data;
    }

    /**
     * Sets the User data in the response.
     *
     * @param data The User object representing the user data to set.
     */
    public void setData(User data) {
        this.data = data;
    }
}
