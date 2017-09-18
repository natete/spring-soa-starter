package com.emergya.sss3E.security.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the request issued to log into the system.
 *
 * @author igonzalez
 * @since 01/06/17.
 */
public class LoginRequest {

    private final String username;
    private final String password;

    /**
     * Default constructor.
     *
     * @param username the username used to log in.
     * @param password the password used to log in.
     */
    @JsonCreator
    public LoginRequest(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username used to log in.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password used to log in.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }
}
