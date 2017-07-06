package com.emergya.sss3e.security.handlers;

import com.emergya.sss3e.security.exceptions.JwtExpiredTokenException;
import com.emergya.sss3e.security.exceptions.JwtInvalidToken;
import com.emergya.sss3e.security.jwt.verifier.TokenVerifier;
import com.emergya.sss3e.security.model.JwtRawAccessToken;
import com.emergya.sss3e.security.model.LogoutRequest;
import com.emergya.sss3e.security.props.SecurityPropsValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles logout requests.
 *
 * @author igonzalez
 * @since 04/07/17.
 */
@Component
public class RevokeTokenHandler implements LogoutHandler {

    private final SecurityPropsValues securityPropsValues;
    private final ObjectMapper mapper;
    private final TokenVerifier tokenVerifier;

    /**
     * Default constructor.
     *
     * @param securityPropsValues an instance of {@link SecurityPropsValues} to get the needed configuration values.
     * @param tokenVerifier an instance of {@link TokenVerifier} to allow token revocation.
     */
    @Autowired
    public RevokeTokenHandler(SecurityPropsValues securityPropsValues, TokenVerifier tokenVerifier) {
        this.securityPropsValues = securityPropsValues;
        this.tokenVerifier = tokenVerifier;
        this.mapper = new ObjectMapper();
    }

    /**
     * Handles the logout request.
     *
     * @param request the received request.
     * @param response the response to be sent.
     * @param authentication the authentication result.
     * @throws AuthenticationException if the received tokens are invalid.
     * @see LogoutHandler#logout(HttpServletRequest, HttpServletResponse, Authentication)
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws AuthenticationException {

        try {
            final LogoutRequest logoutRequest = mapper.readValue(request.getInputStream(), LogoutRequest.class);

            JwtRawAccessToken accessToken = new JwtRawAccessToken(logoutRequest.getAccessToken());
            JwtRawAccessToken refreshToken = new JwtRawAccessToken(logoutRequest.getRefreshToken());

            revokeToken(accessToken);
            revokeToken(refreshToken);

        } catch (IOException e) {
            throw new JwtInvalidToken("Invalid payload");
        }
    }

    /**
     * Extracts the token information catching possible exceptions thrown because of expired tokens.
     *
     * @param token the token to be revoked.
     * @throws AuthenticationException if the token is invalid for a different reason that expired.
     */
    private void revokeToken(JwtRawAccessToken token) throws AuthenticationException {
        try {
            Claims claims = token.parseClaims(securityPropsValues.getJwtSecret()).getBody();

            tokenVerifier.revokeToken(claims);

        } catch (JwtExpiredTokenException ex) {
            // If the token is expired we don't need to blacklist it, so we silently catch this exception.
        }
    }
}
