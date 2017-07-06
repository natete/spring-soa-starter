package com.emergya.sss3e.security.model;

import com.emergya.sss3e.security.props.SecurityPropsValues;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

/**
 * A class that is responsible of tokens creation.
 *
 * @author igonzalez
 * @since 02/07/17.
 */
public abstract class JwtTokenCreator {

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    final SecurityPropsValues securityPropsValues;

    /**
     * Default constructor.
     *
     * @param securityPropsValues an instance of the class holding the properties related to security.
     */
    public JwtTokenCreator(SecurityPropsValues securityPropsValues) {
        this.securityPropsValues = securityPropsValues;
    }

    /**
     * Gets the audience of the token which is used to support multi-tenancy.
     *
     * @return the tenant
     */
    protected abstract String getAudience();

    /**
     * Creates an access token.
     *
     * @param user the user whose information must go in the token.
     * @return the access token.
     */
    public JwtAccessToken createAccessToken(UserContext user) {
        if (StringUtils.isBlank(user.getUsername())) {
            throw new IllegalArgumentException("Username is mandatory");
        }

        if (user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
            throw new IllegalArgumentException("Authorities are mandatory");
        }

        Claims claims = this.buildAccessTokenClaims(user);

        String jwt = this.buildJwt(claims);

        return new JwtAccessToken(jwt);
    }

    /**
     * Creates an refresh token.
     *
     * @param user the user whose information must go in the token.
     * @return the refresh token.
     */
    public JwtToken createRefreshToken(UserContext user) {

        if (StringUtils.isBlank(user.getUsername())) {
            throw new IllegalArgumentException("Username is mandatory");
        }

        Claims claims = this.buildRefreshTokenClaims(user);

        String jwt = this.buildJwt(claims);

        return new JwtAccessToken(jwt);

    }

    /**
     * Part of the strategy pattern implementation. Overriding this method you can add the claims that your application
     * needs in the access token.
     *
     * @param user the user owner of the JWT to be issued.
     * @return the claims required by your application.
     */
    protected abstract Claims buildAccessTokenClaims(UserContext user);

    /**
     * Part of the strategy pattern implementation. Overriding this method you can add the claims that your application
     * needs in the refresh token.
     *
     * @param user the user owner of the JWT to be issued.
     * @return the claims required by your application.
     */
    protected abstract Claims buildRefreshTokenClaims(UserContext user);

    /**
     * Builds the basic structure of the claims.
     *
     * @param user the user owner of the JWT to be issued.
     * @param currentTime the current time used to set the issued time.
     * @return the basic {@link Claims}
     */
    Claims buildBasicClaims(UserContext user, LocalDateTime currentTime) {

        Claims claims = Jwts.claims();

        claims.setId(UUID.randomUUID().toString());
        claims.setSubject(user.getUsername());
        claims.setIssuer(securityPropsValues.getAppName());
        claims.setAudience(this.getAudience()); // To support multi-tenant
        claims.setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()));
        return claims;
    }

    /**
     * Builds the JWT token with the given claims.
     *
     * @param claims the claims to be added to the token.
     * @return the string that represents the token.
     */
    private String buildJwt(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SIGNATURE_ALGORITHM, securityPropsValues.getJwtSecret())
                .compact();
    }
}
