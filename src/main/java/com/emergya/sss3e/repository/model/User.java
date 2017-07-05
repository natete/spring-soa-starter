package com.emergya.sss3e.repository.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity that identifies a user. It establishes as a restriction that there are no users with the same email.
 * 
 * @author iiglesias
 *
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements Serializable {

    private static final long serialVersionUID = 4757177707236477280L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String firstName;

    private String email;

    protected User() {

        // Only it's necessary for JPA constructor, so that defined as protected.

        super();
    }

    public User(String firstName, String email) {
        super();
        this.firstName = firstName;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", firstName=" + firstName + ", email=" + email + "]";
    }

}
