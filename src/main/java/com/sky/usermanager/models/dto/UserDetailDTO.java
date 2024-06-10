package com.sky.usermanager.models.dto;

import com.sky.usermanager.models.common.UserRole;
import com.sky.usermanager.models.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDetailDTO extends UserDTO {
    private final UserRole role;
    private final List<ProjectDTO> projects;

    public UserDetailDTO(long id,
                         String email,
                         String name,
                         UserRole userRole,
                         List<ProjectDTO> projects) {
        super(id, email, name);
        this.role = userRole;
        this.projects = projects;
    }

    public UserRole getRole() {
        return role;
    }

    public List<ProjectDTO> getProjects() {
        if (projects == null) {
            return new ArrayList<>();
        }

        return projects;
    }


    public User toEntity() {
        return new User(getId(), getEmail(), getName(), getRole(), new ArrayList<>());
    }
}
