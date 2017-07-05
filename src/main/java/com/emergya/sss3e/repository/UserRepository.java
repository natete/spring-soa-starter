package com.emergya.sss3e.repository;

import org.springframework.data.repository.CrudRepository;

import com.emergya.sss3e.repository.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Find user by email
     * 
     * @param email
     * @return
     */
    public User findByEmail(String email);

}
