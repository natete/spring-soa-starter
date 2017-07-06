package com.emergya.sss3e.security.providers;

import com.emergya.sss3e.security.model.CustomAuthority;
import com.emergya.sss3e.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * The provider used to grant the access to users using the login endpoint.
 *
 * @author igonzalez
 * @since 04/06/2017
 */
@Component
public class JwtLoginAuthenticationProvider implements AuthenticationProvider {

    private final BCryptPasswordEncoder encoder;
    // Add here as many dependencies as you might need to implement your security such as a jpa repository

    /**
     * Default constructor.
     *
     * @param encoder the encoder used to encode the user password.
     */
    @Autowired
    public JwtLoginAuthenticationProvider(final BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Performs the authentication of the user checking if the provided credentials are valid.
     *
     * @param authentication the {@link Authentication} filtered by the corresponding filter.
     * @return {@link UsernamePasswordAuthenticationToken} the authentication result.
     * @throws AuthenticationException if the credentials are invalid.
     * @see AuthenticationProvider#authenticate(Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication == null) {
            throw new BadCredentialsException("No auth data provided");
        }

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        // Get the user you need to check if the credentials are valid
        final String validUsername = "admin";
        final String validPassword = encoder.encode("admin");

        if (!username.equals(validUsername) || !encoder.matches(password, validPassword)) {
            throw new BadCredentialsException("Invalid password or username");
        }

        UserContext userContext = UserContext.create(validUsername, Collections.singletonList(CustomAuthority.ADMIN));

        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    /**
     * @see AuthenticationProvider#supports(Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
