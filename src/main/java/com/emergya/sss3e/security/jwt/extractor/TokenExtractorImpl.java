package com.emergya.sss3e.security.jwt.extractor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

/**
 * @author igonzalez
 * @see TokenExtractor
 * @since 02/07/17.
 */
@Component
public class TokenExtractorImpl implements TokenExtractor {

    private static final String HEADER_PREFIX = "Bearer ";

    /**
     * @see TokenExtractor#extract(String)
     */
    @Override
    public String extract(String payload) {
        if (StringUtils.isBlank(payload)) {
            throw new AuthenticationServiceException("Authorization header is mandatory");
        }

        if (payload.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Incorrect authorization header size");
        }
        return payload.substring(HEADER_PREFIX.length(), payload.length());
    }
}
