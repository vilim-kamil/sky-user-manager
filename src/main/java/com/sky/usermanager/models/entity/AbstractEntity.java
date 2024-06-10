package com.sky.usermanager.models.entity;

import com.sky.usermanager.models.dto.BaseDTO;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class AbstractEntity<T extends BaseDTO> {
    public abstract T mapToDto();
    public abstract T mapToDetailDto();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public AbstractEntity(Long id) {
        this.id = id;
    }

    public AbstractEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
