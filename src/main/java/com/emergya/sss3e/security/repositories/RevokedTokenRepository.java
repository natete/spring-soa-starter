package com.emergya.sss3e.security.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * The Redis repository of the revoked tokens.
 *
 * @author igonzalez
 * @since 04/07/17.
 */
@Repository
public class RevokedTokenRepository {

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Default constructor.
     *
     * @param redisTemplate the {@link RedisTemplate} used by the repository.
     */
    @Autowired
    public RevokedTokenRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Adds a revoked token to the repository with the given key and the given expiration time.
     *
     * @param revokedToken the key that represents the revoked token.
     * @param expirationTime the time at which the token should disappear from the repository.
     */
    public void addRevokedToken(String revokedToken, Date expirationTime) {
        BoundValueOperations<String, String> boundValueOperations = redisTemplate.boundValueOps(revokedToken);
        boundValueOperations.set(""); // We don't need any content so we set an empty string.
        boundValueOperations.expireAt(expirationTime);
    }

    /**
     * Returns true if the given token is present in the repository.
     *
     * @param revokedToken the token whose existence must be checked.
     * @return true if the token is in the repository, false otherwise.
     */
    public boolean existsToken(String revokedToken) {
        return redisTemplate.getConnectionFactory().getConnection().exists(revokedToken.getBytes());
    }
}
