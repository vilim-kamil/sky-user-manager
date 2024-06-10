package com.sky.usermanager.models.dto;

public class UserDTO extends BaseDTO {
    private final String email;
    private final String name;

    public UserDTO(long id, String email, String name) {
        super(id);
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
