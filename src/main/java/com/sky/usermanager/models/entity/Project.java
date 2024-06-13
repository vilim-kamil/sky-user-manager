package com.sky.usermanager.models.entity;

import com.sky.usermanager.models.dto.ProjectDTO;
import com.sky.usermanager.models.dto.ProjectDetailDTO;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_external_projects")
public class Project extends AbstractEntity<ProjectDTO> {

    @Column(name = "name", unique = true)
    private String name;

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
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    public Project(Long id, String name) {
        super(id);
        this.name = name;
    }

    public Project() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public ProjectDTO mapToDto() {
        return new ProjectDTO(
                getId(),
                name
        );
    }

    @Override
    public ProjectDetailDTO mapToDetailDto() {
        return new ProjectDetailDTO(
                getId(),
                name,
                users.stream().map(User::mapToDto).collect(Collectors.toList())
        );
    }
}
