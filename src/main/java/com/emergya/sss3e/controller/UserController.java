package com.emergya.sss3e.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emergya.sss3e.aop.AuditMethod;
import com.emergya.sss3e.blogic.UserBLogic;
import com.emergya.sss3e.controller.handler.ExceptionsHandler;
import com.emergya.sss3e.repository.model.User;

/**
 * Controller that contains user methods
 * 
 * @author iiglesias
 *
 */
@RestController
@RequestMapping(value = "/user")
@Api(value = "Services that contains user methods")
public class UserController extends ExceptionsHandler {

    @Autowired
    private UserBLogic userBLogic;

    /**
     * Get all users
     * 
     * @return
     */
    @GetMapping
    @ApiOperation(value = "Service to get all users")
    @AuditMethod(level = { AuditMethod.Level.NAME })
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userBLogic.getUsers());
    }

    /**
     * Get user by email
     * 
     * @param email
     * @return
     */
    @GetMapping(value = "/email/{email}")
    @ApiOperation(value = "Service to get user by email")
    @AuditMethod(level = { AuditMethod.Level.NAME })
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(userBLogic.getUserByEmail(email));
    }

    /**
     * Create a new user
     * 
     * @param user
     * @return
     */
    @PostMapping
    @ApiOperation(value = "Service to create a new user")
    @AuditMethod(level = { AuditMethod.Level.NAME })
    public ResponseEntity<User> create(@RequestBody User user) {

        user.setUserId(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(userBLogic.createOrUpdate(user));
    }

    /**
     * Update a user
     * 
     * @param user
     * @return
     */
    @PutMapping(value = "/{userId}")
    @ApiOperation(value = "Service to update a user")
    @AuditMethod(level = { AuditMethod.Level.NAME })
    public ResponseEntity<User> update(@PathVariable Long userId, @RequestBody User user) {

        user.setUserId(userId);

        return ResponseEntity.ok().body(userBLogic.createOrUpdate(user));
    }

}
