package com.sky.usermanager.rest;

import com.sky.usermanager.models.dto.ProjectDTO;
import com.sky.usermanager.rest.handlers.ObjectErrorResponse;
import com.sky.usermanager.services.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/projects")
public class ProjectsAdminController {

    private ProjectsService projectsService;

    @Autowired
    public ProjectsAdminController(@Qualifier("projectsService") ProjectsService projectsService) {
        this.projectsService = projectsService;
    }

    @PostMapping
    public ProjectDTO createProject(@RequestBody ProjectDTO project) {
        return projectsService.createProject(project);
    }

    @PutMapping("/{id}")
    public ProjectDTO updateProject(@PathVariable("id") long id, @RequestBody ProjectDTO project) {
        if (projectsService.exists(id)) {
            return projectsService.updateProject(id, project);
        } else throw new ObjectErrorResponse.NotFoundException();
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable("id") long id) {
        if (projectsService.exists(id)) {
            projectsService.deleteProject(id);
        } else throw new ObjectErrorResponse.NotFoundException();
    }
}
