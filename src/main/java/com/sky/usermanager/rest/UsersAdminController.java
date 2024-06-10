package com.sky.usermanager.rest;

import com.sky.usermanager.models.dto.ProjectDTO;
import com.sky.usermanager.models.dto.UserDetailDTO;
import com.sky.usermanager.rest.handlers.ObjectErrorResponse;
import com.sky.usermanager.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
public class UsersAdminController {

    private UsersService usersService;

    @Autowired
    public UsersAdminController(@Qualifier("usersService") UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public String createUser(@RequestBody UserDetailDTO user) {
        return usersService.createUser(user);
    }

    @PutMapping("/{id}")
    public UserDetailDTO manageUser(@PathVariable("id") long id, @RequestBody UserDetailDTO userRequest) {
        if (usersService.exists(id)) {
            return usersService.updateUserPermissions(id, userRequest);
        } else throw new ObjectErrorResponse.NotFoundException();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        if (usersService.exists(id)) {
            usersService.deleteUser(id);
        } else throw new ObjectErrorResponse.NotFoundException();
    }

    @PutMapping("/{id}/projects")
    public UserDetailDTO assignProject(@PathVariable("id") long userId, @RequestBody ProjectDTO projectRequest) {
        if (usersService.exists(userId)) {
            return usersService.addUserProject(userId, projectRequest.getId());
        } else throw new ObjectErrorResponse.NotFoundException();
    }

    @DeleteMapping("/{userId}/projects/{projectId}")
    public UserDetailDTO removeProject(@PathVariable("userId") long userId, @PathVariable("projectId") long projectId) {
        if (usersService.exists(userId)) {
            return usersService.removeUserProject(userId, projectId);
        } else throw new ObjectErrorResponse.NotFoundException();
    }
}
