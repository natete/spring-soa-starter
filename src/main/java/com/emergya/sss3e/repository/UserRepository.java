package com.emergya.sss3e.repository;

import org.springframework.data.repository.CrudRepository;

import com.emergya.sss3e.repository.model.User;

/**
 * User repository that extend JPA Repository. You can find more info about JPA repositories types in
 * (https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.definition).
 * 
 * Also, you can find information about query creation mechanism for JPA in
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
 * 
 * @author iiglesias
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Find user by email
     * 
     * @param email
     * @return
     */
    public User findByEmail(String email);

}
