package com.sky.usermanager.models.entity;

import com.sky.usermanager.models.common.UserRole;
import com.sky.usermanager.models.dto.UserDTO;
import com.sky.usermanager.models.dto.UserDetailDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_users")
public class User extends AbstractEntity<UserDTO> {

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH
            })
    @JoinTable(
            name = "tb_user_external_projects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;

    public User(Long id, String email, String name, UserRole role, List<Project> projects) {
        super(id);

        this.email = email;
        this.name = name;
        this.role = role;
        this.projects = projects;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        if (projects == null) {
            projects = new ArrayList<>();
        }

        projects.add(project);
    }

    public void removeProject(long projectId) {
        if (projects == null) {
            projects = new ArrayList<>();
        }

        projects.removeIf(project -> project.getId().equals(projectId));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public UserDTO mapToDto() {
        return new UserDTO(
                getId(),
                email,
                name
        );
    }

    @Override
    public UserDetailDTO mapToDetailDto() {
        return new UserDetailDTO(
                getId(),
                email,
                name,
                role,
                projects.stream().map(Project::mapToDto).collect(Collectors.toList())
        );
    }
}
