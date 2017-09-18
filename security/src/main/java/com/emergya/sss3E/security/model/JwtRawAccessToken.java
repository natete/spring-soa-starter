package com.emergya.sss3E.security.model;

import com.emergya.sss3E.security.exceptions.JwtExpiredTokenException;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

/**
 * Represents a raw access token and it controls the Claims extraction.
 *
 * @see JwtToken
 */
public class JwtRawAccessToken implements JwtToken {
    private final String token;

    /**
     * Default constructor.
     *
     * @param token the string that represents the token.
     */
    public JwtRawAccessToken(String token) {
        this.token = token;
    }

    /**
     * Extracts the claims associated to the token.
     *
     * @param signingKey the key to be used to decode the token.
     * @return {@link Jws<Claims>}
     * @throws AuthenticationException if an error occurs when parsing the claims of the token.
     */
    public Jws<Claims> parseClaims(String signingKey) throws AuthenticationException {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            throw new BadCredentialsException(ex.getMessage(), ex);
        } catch (ExpiredJwtException expiredEx) {
            throw new JwtExpiredTokenException("Expired token", expiredEx);
        }
    }

    /**
     * @return the token.
     * @see JwtToken#getToken()
     */
    @Override
    public String getToken() {
        return token;
    }
}
