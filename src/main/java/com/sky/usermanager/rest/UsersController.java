package com.sky.usermanager.rest;

import com.sky.usermanager.models.dto.UserDTO;
import com.sky.usermanager.models.dto.UserDetailDTO;
import com.sky.usermanager.rest.handlers.ObjectErrorResponse;
import com.sky.usermanager.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UsersService usersService;

    @Autowired
    public UsersController(@Qualifier("usersService") UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    @ResponseBody
    public List<UserDTO> getAll() {
        return usersService.getUsers();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public UserDetailDTO getById(@PathVariable("id") long id) {
        Optional<UserDetailDTO> user = usersService.getUser(id);
        if (user.isPresent()) {
            return user.get();
        } else throw new ObjectErrorResponse.NotFoundException();
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable("id") long id, @RequestBody UserDTO userRequest, Principal principal) {
        if (userRequest.getEmail().equalsIgnoreCase(principal.getName())) {
            if (!usersService.exists(id)) {
                throw new ObjectErrorResponse.NotFoundException();
            }

            return usersService.updateUserInfo(id, userRequest);
        } else throw new ObjectErrorResponse.ForbiddenException();
    }

    @PutMapping
    public void updatePassword(@RequestBody String newPassword, Principal principal) {
        usersService.updatePassword(principal.getName(), newPassword);
    }
}
