package com.sky.usermanager.dao;

import com.sky.usermanager.models.dto.UserDTO;
import com.sky.usermanager.models.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends BaseRepository<UserDTO, User> {

    /**
     * User specific query to retrieve used by email.
     * Required since Spring Security only passed reference to user id (in this case email field).
     *
     * @param email unique user identified
     * @return nullable user object
     */
    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);
}
