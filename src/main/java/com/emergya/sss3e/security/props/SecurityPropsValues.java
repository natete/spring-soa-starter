package com.emergya.sss3e.security.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Values of the keys in the property resource security.properties. Return the property value associated with the given key,
 * or null if the key cannot be resolved.
 *
 * @author igonzalez
 * @since 05/07/2017
 */
@Component
public class SecurityPropsValues {

    /*
     * NOTE: Sort the private variables in alphabetical order
     */

    @Value("${" + SecurityPropsKeys.APP_NAME + "}")
    private String appName;

    @Value("${" + SecurityPropsKeys.JWT_ACCESS_TOKEN_EXPIRATION_TIME + "}")
    private int jwtAccessTokenExpirationTime;

    @Value("${" + SecurityPropsKeys.JWT_REFRESH_TOKEN_EXPIRATION_TIME + "}")
    private int jwtRefreshTokenExpirationTime;

    @Value("${" + SecurityPropsKeys.JWT_SECRET + "}")
    private String jwtSecret;

    /**
     * Returns the app name.
     *
     * @return the app name.
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Returns the expiration time of the access token in minutes.
     *
     * @return the expiration time of the access token in minutes.
     */
    public int getJwtAccessTokenExpirationTime() {
        return jwtAccessTokenExpirationTime;
    }

    /**
     * Returns the expiration time of the refresh token in minutes.
     *
     * @return the expiration time of the refresh token in minutes.
     */
    public int getJwtRefreshTokenExpirationTime() {
        return jwtRefreshTokenExpirationTime;
    }

    /**
     * Returns the application secret used to sign the tokens.
     *
     * @return the application secret used to sign the tokens.
     */
    public String getJwtSecret() {
        return jwtSecret;
    }
}
