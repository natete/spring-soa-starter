package com.emergya.sss3e.blogic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.emergya.sss3e.repository.UserRepository;
import com.emergya.sss3e.repository.model.User;
import com.google.common.collect.Lists;

/**
 * Business logic for the user module
 * 
 * @author iiglesias
 *
 */
@Component
@Transactional(readOnly = true)
public class UserBLogic {

    @Autowired
    private UserRepository userRepository;

    /**
     * Returns all users
     * 
     * @return
     */
    public List<User> getUsers() {
        return Lists.newArrayList(userRepository.findAll());
    }

    /**
     * Return user by email
     * 
     * @param email
     * @return
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Create or update a user
     * 
     * @param user
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public User createOrUpdate(User user) {
        return userRepository.save(user);
    }
}
