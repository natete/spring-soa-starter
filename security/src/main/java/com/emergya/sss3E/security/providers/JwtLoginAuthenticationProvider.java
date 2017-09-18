package com.emergya.sss3E.security.providers;

import com.emergya.sss3E.security.model.UserContext;
import com.emergya.sss3E.security.props.SecurityPropsValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * The provider used to grant the access to users using the login endpoint.
 *
 * @author igonzalez
 * @since 04/06/2017
 */
@Component
public class JwtLoginAuthenticationProvider implements AuthenticationProvider {

    private final BCryptPasswordEncoder encoder;

    private final SecurityPropsValues propsValues;

    /**
     * Default constructor.
     *
     * @param encoder the encoder used to encode the user password.
     * @param propsValues  class that provides spring properties.
     */
    @Autowired
    public JwtLoginAuthenticationProvider(final BCryptPasswordEncoder encoder, SecurityPropsValues propsValues) {
        this.encoder = encoder;
        this.propsValues = propsValues;
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

        UserContext userContext;
        if (username.equals("admin") && password.equals(propsValues.getAdminPassword())) {
            // TODO: Remove this backdoor
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("SUPERADMIN"));
            userContext = UserContext.create("admin", authorities);

        } else {
            // TODO: add valid user checking
            throw new BadCredentialsException("Invalid username or password");
        }

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
