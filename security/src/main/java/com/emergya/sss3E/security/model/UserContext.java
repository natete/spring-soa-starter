package com.emergya.sss3E.security.model;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Represents a user of the system defined by username and roles.
 *
 * @author igonzalez
 * @since 02/07/17.
 */
public class UserContext {

    private final String username;
    private final Collection<GrantedAuthority> authorities;

    /**
     * Private constructor.
     * @param username the username of the user.
     * @param authorities the authorities granted to the user.
     */
    private UserContext(String username, Collection<GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;

    }

    /**
     * Creator of the class.
     *
     * @param username the username to be used to create the UserContext.
     * @param authorities the authorities of the user to be created.
     * @return the UserContext instance.
     * @throws AuthenticationException if the username or the authorities are invalid.
     */
    public static UserContext create(String username, Collection<GrantedAuthority> authorities)
            throws AuthenticationException {
        if (StringUtils.isBlank(username) || authorities == null || authorities.isEmpty()) {
            throw new BadCredentialsException("Username is blank: " + username);
        }

        return new UserContext(username, authorities);
    }

    /**
     * Returns the username.
     *
     * @return the username.
     */
    String getUsername() {
        return username;
    }

    /**
     * Returns the authorities of the user.
     *
     * @return the authorities of the user.
     */
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

}
