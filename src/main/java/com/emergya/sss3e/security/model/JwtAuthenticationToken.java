package com.emergya.sss3e.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Represents an authentication token on the process of being validated.
 *
 * @author igonzalez
 * @see AbstractAuthenticationToken
 * @since 02/07/17.
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private JwtRawAccessToken rawAccessToken;
    private UserContext userContext;

    /**
     * Constructor of a token not yet validated.
     *
     * @param unsafeToken the token to be validated.
     */
    public JwtAuthenticationToken(JwtRawAccessToken unsafeToken) {
        super(null);
        this.rawAccessToken = unsafeToken;
        this.userContext = null;
        this.setAuthenticated(false);
    }

    /**
     * Constructor of a valid user.
     *
     * @param user the valid user extracted from the request.
     * @param authorities the authorities that the user holds
     */
    public JwtAuthenticationToken(UserContext user, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.userContext = user;
        this.rawAccessToken = null;
        super.setAuthenticated(true);
    }

    /**
     * @see AbstractAuthenticationToken#setAuthenticated(boolean)
     */
    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    /**
     * @return the credentials represented by a {@link JwtRawAccessToken}
     * @see AbstractAuthenticationToken#getCredentials()
     */
    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    /**
     * @return the credentials represented by a {@link UserContext}
     * @see AbstractAuthenticationToken#getPrincipal()
     */
    @Override
    public Object getPrincipal() {
        return this.userContext;
    }

    /**
     * @see AbstractAuthenticationToken#eraseCredentials()
     */
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.rawAccessToken = null;
        this.userContext = null;
    }
}
