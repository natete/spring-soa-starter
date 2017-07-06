package com.emergya.sss3e.security.model;

import com.emergya.sss3e.security.props.SecurityPropsValues;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @see JwtTokenCreator
 */
@Component
public class JwtTokenCreatorImpl extends JwtTokenCreator {

    public static final String ROLES_KEY = "roles";

    /**
     * @see JwtTokenCreator#JwtTokenCreator(SecurityPropsValues)
     */
    @Autowired
    public JwtTokenCreatorImpl(SecurityPropsValues propsValues) {
        super(propsValues);
    }

    /**
     * Adds the corresponding expiration time and the roles of the user to the claims.
     *
     * @param user the user owner of the JWT to be issued.
     * @return the claims required for the access token.
     * @see JwtTokenCreator#buildAccessTokenClaims(UserContext)
     */
    @Override
    protected Claims buildAccessTokenClaims(UserContext user) {
        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = this.buildBasicClaims(user, currentTime);
        claims.setExpiration(Date
                .from(currentTime.plusMinutes(this.securityPropsValues.getJwtAccessTokenExpirationTime())
                        .atZone(ZoneId.systemDefault()).toInstant()));

        claims.put(ROLES_KEY, user.getAuthorities().stream().map(CustomAuthority::getAuthority)
                .collect(Collectors.toList()));

        return claims;
    }

    /**
     * Adds the corresponding expiration time and the REFRESH as the role to the claims.
     *
     * @param user the user owner of the JWT to be issued.
     * @return the claims required for the refresh token.
     * @see JwtTokenCreator#buildRefreshTokenClaims(UserContext)
     */
    @Override
    protected Claims buildRefreshTokenClaims(UserContext user) {

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = this.buildBasicClaims(user, currentTime);
        claims.setExpiration(Date
                .from(currentTime.plusMinutes(this.securityPropsValues.getJwtRefreshTokenExpirationTime())
                        .atZone(ZoneId.systemDefault()).toInstant()));

        claims.put(ROLES_KEY, Collections.singletonList(CustomAuthority.REFRESH.getAuthority()));

        return claims;
    }

    /**
     * Returns the audience of the token being issued.
     *
     * @return the audience of the token being issued.
     */
    @Override
    protected String getAudience() {
        return "";
    }
}
