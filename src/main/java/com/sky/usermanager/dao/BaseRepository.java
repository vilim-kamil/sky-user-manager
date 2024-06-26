package com.sky.usermanager.dao;

import com.sky.usermanager.models.dto.BaseDTO;
import com.sky.usermanager.models.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base repository used to enforce potential typing in services.
 *
 * @param <T> DTO object used for data transfer
 * @param <U> Entity object used for data persistence
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseDTO, U extends AbstractEntity<T>> extends JpaRepository<U, Long> {
}
