package com.sky.usermanager.services;

import com.sky.usermanager.models.dto.UserDTO;
import com.sky.usermanager.models.dto.UserDetailDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("usersService")
@Profile("test")
public class UsersServiceStub implements UsersService {

    @Override
    public boolean exists(long id) {
        return true;
    }

    @Override
    public List<UserDTO> getUsers() {
        return new ArrayList<>();
    }

    @Override
    public Optional<UserDetailDTO> getUser(long id) {
        return Optional.empty();
    }

    @Override
    public String createUser(UserDetailDTO user) {
        return null;
    }

    @Override
    public UserDTO updateUserInfo(long id, UserDTO user) {
        return user;
    }

    @Override
    public UserDetailDTO updateUserPermissions(long id, UserDetailDTO user) {
        return user;
    }

    @Override
    public void updatePassword(String email, String password) {
    }

    @Override
    public void deleteUser(long id) {
    }

    @Override
    public UserDetailDTO addUserProject(long userId, long projectId) {
        return null;
    }

    @Override
    public UserDetailDTO removeUserProject(long userId, long projectId) {
        return null;
    }
}
