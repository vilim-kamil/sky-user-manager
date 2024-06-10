package com.sky.usermanager.models.dto;

import jakarta.annotation.Nullable;

public abstract class BaseDTO {
    private final Long id;

    public BaseDTO(@Nullable Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
