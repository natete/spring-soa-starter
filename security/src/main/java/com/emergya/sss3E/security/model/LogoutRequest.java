package com.emergya.sss3E.security.model;

import com.emergya.sss3E.security.handlers.JwtAuthenticationSuccessHandler;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the request issued to perform a logout.
 *
 * @author igonzalez
 * @since 01/06/17.
 */
public class LogoutRequest {

    private final String accessToken;
    private final String refreshToken;

    /**
     * Default creator.
     *
     * @param accessToken the access token to be revoked.
     * @param refreshToken the refresh token to be revoked.
     */
    @JsonCreator
    public LogoutRequest(@JsonProperty(JwtAuthenticationSuccessHandler.ACCESS_TOKEN) String accessToken,
            @JsonProperty(JwtAuthenticationSuccessHandler.REFRESH_TOKEN) String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    /**
     * The access token used to logout.
     *
     * @return the access token used to logout.
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * The refresh token used to logout.
     *
     * @return the refresh token used to logout.
     */
    public String getRefreshToken() {
        return refreshToken;
    }
}
