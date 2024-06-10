package com.sky.usermanager.models.dto;

import com.sky.usermanager.models.entity.Project;

public class ProjectDTO extends BaseDTO {
    private final String name;

    public ProjectDTO(long id, String name) {
        super(id);
        this.name = name;
    }

    public Project toEntity() {
        return new Project(getId(), name);
    }

    public String getName() {
        return name;
    }
}
