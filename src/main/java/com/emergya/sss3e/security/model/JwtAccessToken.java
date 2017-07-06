package com.emergya.sss3e.security.model;

/**
 * Represents an access token.
 *
 * @author igonzalez
 * @since 02/07/17.
 */
public class JwtAccessToken implements JwtToken {

    private final String token;

    /**
     * Default constructor.
     *
     * @param token the token
     */
    JwtAccessToken(String token) {
        this.token = token;
    }

    /**
     * @return a String representing the token.
     * @see JwtToken#getToken()
     */
    @Override
    public String getToken() {
        return this.token;
    }
}
