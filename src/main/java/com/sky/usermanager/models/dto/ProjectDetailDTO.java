package com.sky.usermanager.models.dto;

import java.util.List;

public class ProjectDetailDTO extends ProjectDTO {
    private final List<UserDTO> users;

    public ProjectDetailDTO(long id, String name, List<UserDTO> users) {
        super(id, name);
        this.users = users;
    }

    public List<UserDTO> getUsers() {
        return users;
    }
}
