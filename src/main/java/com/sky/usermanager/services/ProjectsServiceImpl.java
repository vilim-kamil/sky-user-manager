package com.sky.usermanager.services;

import com.sky.usermanager.dao.ProjectsRepository;
import com.sky.usermanager.models.dto.ProjectDTO;
import com.sky.usermanager.models.dto.ProjectDetailDTO;
import com.sky.usermanager.models.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("projectsService")
@Profile("default")
public class ProjectsServiceImpl implements ProjectsService {

    private final ProjectsRepository projectsRepository;

    @Autowired
    public ProjectsServiceImpl(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    @Override
    public boolean exists(long id) {
        return projectsRepository.existsById(id);
    }

    @Override
    public List<ProjectDTO> getProjects() {
        return projectsRepository.findAll().stream().map(project -> new ProjectDTO(
                project.getId(),
                project.getName()
        )).collect(Collectors.toList());
    }

    @Override
    public Optional<ProjectDetailDTO> getProject(long id) {
        return projectsRepository.findById(id).map(Project::mapToDetailDto);
    }

    @Override
    public ProjectDTO createProject(ProjectDTO project) {
        return projectsRepository.save(project.toEntity()).mapToDto();
    }

    @Override
    public ProjectDTO updateProject(long id, ProjectDTO project) {
        Optional<Project> existingProject = projectsRepository.findById(id);

        Project updatedProject = existingProject.get();
        updatedProject.setName(project.getName());
        return projectsRepository.save(updatedProject).mapToDto();
    }

    @Override
    public void deleteProject(long id) {
        projectsRepository.deleteById(id);
    }
}
