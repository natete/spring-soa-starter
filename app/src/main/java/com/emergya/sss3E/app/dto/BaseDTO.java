package com.emergya.sss3E.app.dto;

public class BaseDTO {

    protected Long id;

    public BaseDTO() {
    }

    public Long getId() {
        return id;
    }

    // @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }
}
