package com.emergya.sss3E.security.jwt.verifier;

import io.jsonwebtoken.Claims;

/**
 * Handles token revocation.
 *
 * @author igonzalez
 * @since 04/07/17.
 */
public interface TokenVerifier {

    /**
     * Checks if the token represented by the given claims is present in the list of revoked tokens.
     *
     * @param claims the {@link Claims} that represent the token to be checked.
     * @return true if the token is present in the list of revoked tokens. False otherwise.
     */
    boolean isRevoked(Claims claims);

    /**
     * Revokes the token represented by the given claims by adding it to the list of revoked tokens.
     *
     * @param claims the {@link Claims} that represent the token to be revoked.
     */
    void revokeToken(Claims claims);
}
