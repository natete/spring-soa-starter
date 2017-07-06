package com.emergya.sss3e.security.providers;

import com.emergya.sss3e.security.exceptions.JwtInvalidToken;
import com.emergya.sss3e.security.jwt.verifier.TokenVerifier;
import com.emergya.sss3e.security.model.CustomAuthority;
import com.emergya.sss3e.security.model.JwtAuthenticationToken;
import com.emergya.sss3e.security.model.JwtRawAccessToken;
import com.emergya.sss3e.security.model.JwtTokenCreatorImpl;
import com.emergya.sss3e.security.model.UserContext;
import com.emergya.sss3e.security.props.SecurityPropsValues;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The provider used to grant the access to users that have a JWT.
 *
 * @author igonzalez
 * @since 02/07/17.
 */
@Component
public class JwtTokenAuthenticationProvider implements AuthenticationProvider {

    private final SecurityPropsValues securityPropsValues;
    private final TokenVerifier tokenVerifier;

    /**
     * Default constructor.
     *
     * @param securityPropsValues an instance of the class holding the properties related to security.
     * @param tokenVerifier an instance of the {@link TokenVerifier} used to check if the token has been revoked.
     */
    @Autowired
    public JwtTokenAuthenticationProvider(SecurityPropsValues securityPropsValues, TokenVerifier tokenVerifier) {
        this.securityPropsValues = securityPropsValues;
        this.tokenVerifier = tokenVerifier;
    }

    /**
     * Performs the authentication of the user checking if the provided token is valid.
     *
     * @param authentication the {@link Authentication} filtered by the corresponding filter.
     * @return {@link UsernamePasswordAuthenticationToken} the authentication result.
     * @throws AuthenticationException if the token is invalid.
     * @see AuthenticationProvider#authenticate(Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtRawAccessToken rawAccessToken = (JwtRawAccessToken) authentication.getCredentials();

        Claims claims = rawAccessToken.parseClaims(securityPropsValues.getJwtSecret()).getBody();

        if (tokenVerifier.isRevoked(claims)) {
            throw new JwtInvalidToken("The token has been revoked");
        }

        String subject = claims.getSubject();
        List<String> roles = claims.get(JwtTokenCreatorImpl.ROLES_KEY, List.class);
        List<CustomAuthority> authorities = roles.stream().map(CustomAuthority::valueOf).collect(Collectors.toList());

        UserContext userContext = UserContext.create(subject, authorities);

        if (roles.contains(CustomAuthority.REFRESH.getAuthority())) {
            return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
        } else {
            return new JwtAuthenticationToken(userContext, authorities);
        }
    }

    /**
     * @see AuthenticationProvider#supports(Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
