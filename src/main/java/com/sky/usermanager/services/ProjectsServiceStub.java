package com.sky.usermanager.services;

import com.sky.usermanager.models.dto.ProjectDTO;
import com.sky.usermanager.models.dto.ProjectDetailDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("projectsService")
@Profile("test")
public class ProjectsServiceStub implements ProjectsService {

    @Override
    public boolean exists(long id) {
        return true;
    }

    @Override
    public List<ProjectDTO> getProjects() {
        return new ArrayList<>();
    }

    @Override
    public Optional<ProjectDetailDTO> getProject(long id) {
        return Optional.empty();
    }

    @Override
    public ProjectDTO createProject(ProjectDTO project) {
        return project;
    }

    @Override
    public ProjectDTO updateProject(long id, ProjectDTO project) {
        return project;
    }

    @Override
    public void deleteProject(long id) {
    }
}
