package com.sky.usermanager.services;

import com.sky.usermanager.dao.ProjectsRepository;
import com.sky.usermanager.dao.UsersRepository;
import com.sky.usermanager.models.dto.UserDTO;
import com.sky.usermanager.models.dto.UserDetailDTO;
import com.sky.usermanager.models.entity.Project;
import com.sky.usermanager.models.entity.User;
import com.sky.usermanager.rest.handlers.ObjectErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("usersService")
@Profile("default")
public class UsersServiceImpl implements UsersService {

    private final ProjectsRepository projectsRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(ProjectsRepository projectsRepository, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.projectsRepository = projectsRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean exists(long id) {
        return usersRepository.existsById(id);
    }

    @Override
    public List<UserDTO> getUsers() {
        return usersRepository.findAll().stream().map(User::mapToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDetailDTO> getUser(long id) {
        return usersRepository.findById(id).map(User::mapToDetailDto);
    }

    @Override
    public String createUser(UserDetailDTO user) {
        User userEntity = user.toEntity();
        String defaultPassword = UUID.randomUUID().toString();
        userEntity.setPassword(passwordEncoder.encode(defaultPassword));
        usersRepository.save(userEntity);
        return defaultPassword;
    }

    @Override
    public UserDTO updateUserInfo(long id, UserDTO user) {
        Optional<User> existingUser = usersRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new ObjectErrorResponse.NotFoundException();
        }

        User userUpdated = existingUser.get();
        userUpdated.setName(user.getName());
        userUpdated.setEmail(user.getEmail());
        return usersRepository.save(userUpdated).mapToDto();
    }

    @Override
    public UserDetailDTO updateUserPermissions(long id, UserDetailDTO user) {
        Optional<User> existingUser = usersRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new ObjectErrorResponse.NotFoundException();
        }

        User userUpdated = existingUser.get();
        userUpdated.setEmail(user.getEmail());
        userUpdated.setName(user.getName());
        userUpdated.setRole(user.getRole());
        return usersRepository.save(userUpdated).mapToDetailDto();
    }

    @Override
    public void updatePassword(String email, String password) {
        User user = usersRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        usersRepository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UserDetailDTO addUserProject(long userId, long projectId) {
        Optional<User> user = usersRepository.findById(userId);
        Optional<Project> project = projectsRepository.findById(projectId);
        if (user.isEmpty() || project.isEmpty()) {
            throw new ObjectErrorResponse.NotFoundException();
        }

        user.get().addProject(project.get());
        return usersRepository.save(user.get()).mapToDetailDto();
    }

    @Override
    public UserDetailDTO removeUserProject(long userId, long projectId) {
        Optional<User> user = usersRepository.findById(userId);
        Optional<Project> project = projectsRepository.findById(projectId);
        if (user.isEmpty() || project.isEmpty()) {
            throw new ObjectErrorResponse.NotFoundException();
        }

        user.get().removeProject(projectId);
        return usersRepository.save(user.get()).mapToDetailDto();
    }
}
