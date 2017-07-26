package com.emergya.sss3E.app.dto;

import com.emergya.sss3E.app.model.User;

import java.io.Serializable;

public class UserDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = -5798981749282036319L;

    private String username;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
