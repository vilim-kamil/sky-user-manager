package com.sky.usermanager.rest;

import com.sky.usermanager.models.dto.ProjectDTO;
import com.sky.usermanager.models.dto.ProjectDetailDTO;
import com.sky.usermanager.rest.handlers.ObjectErrorResponse;
import com.sky.usermanager.services.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectsController {

    private final ProjectsService projectsService;

    @Autowired
    public ProjectsController(@Qualifier("projectsService") ProjectsService projectsService) {
        this.projectsService = projectsService;
    }

    @GetMapping
    @ResponseBody
    public List<ProjectDTO> getAll() {
        return projectsService.getProjects();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ProjectDetailDTO getById(@PathVariable("id") long id) {
        Optional<ProjectDetailDTO> project = projectsService.getProject(id);
        if (project.isPresent()) {
            return project.get();
        } else throw new ObjectErrorResponse.NotFoundException();
    }
}
