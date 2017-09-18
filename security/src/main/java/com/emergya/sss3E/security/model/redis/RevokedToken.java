package com.emergya.sss3E.security.model.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Represents a revoked token to be stored in Redis.
 *
 * @author igonzalez
 * @since 04/07/17.
 */
@RedisHash("revoked_tokens")
class RevokedToken {

    @Id
    private String key;

    /**
     * Empty constructor.
     */
    protected RevokedToken() {
        super();
    }

    /**
     * Default constructor.
     *
     * @param key the key to be stored.
     */
    public RevokedToken(String key) {
        this.key = key;
    }

    /**
     * Sets new key.
     *
     * @param key New value of key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets key.
     *
     * @return Value of key.
     */
    public String getKey() {
        return key;
    }
}
