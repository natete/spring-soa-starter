package com.emergya.sss3e.security.jwt.extractor;

/**
 * Extracts the token from a given string.
 *
 * @author igonzalez
 * @since 02/07/17.
 */
public interface TokenExtractor {

    /**
     * Extracts the token from a given string.
     *
     * @param payload the string to extract the token from.
     * @return the extracted token.
     */
    String extract(String payload);
}
