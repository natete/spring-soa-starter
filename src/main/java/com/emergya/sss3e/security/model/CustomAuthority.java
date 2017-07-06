package com.emergya.sss3e.security.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enum that represents the granted authorities present in the system.
 *
 * @author igonzalez
 * @since 02/07/17.
 */
public enum CustomAuthority implements GrantedAuthority {

    REFRESH("REFRESH"), ADMIN("ADMIN");

    private final String authority;

    /**
     * Default constructor.
     *
     * @param authority the authority.
     */
    CustomAuthority(String authority) {
        this.authority = authority;
    }

    /**
     * @see GrantedAuthority#getAuthority()
     */
    @Override
    public String getAuthority() {
        return this.authority;
    }
}
