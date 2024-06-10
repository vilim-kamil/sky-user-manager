package com.sky.usermanager.services;

import com.sky.usermanager.models.dto.UserDTO;
import com.sky.usermanager.models.dto.UserDetailDTO;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Handler for retrieval and persistence of users data.
 * Since both base and admin endpoints process the data in
 * a similar fashion, a single service is used - if scalability would
 * be a concern, this would need to be refactored.
 *
 * @see com.sky.usermanager.dao.UsersRepository
 * @see com.sky.usermanager.models.entity.User
 * @see com.sky.usermanager.models.dto.UserDetailDTO
 */
public interface UsersService {

    /**
     * Checks if an object with the supplied ID exists.
     * This method should not query the object, since it is supposed
     * to serve as a more efficient way to check for existence than query
     * and check for nullability.
     *
     * @param id ID of the required object
     * @return true if object exists in the database, false otherwise
     */
    boolean exists(long id);

    /**
     * Queries a list of all users persisted within the database.
     *
     * @return list of all persisted users
     */
    List<UserDTO> getUsers();

    /**
     * Queries single user based on the ID field.
     * Since the result is nullable, it is recommended to check for its existence with {@link #exists(long)}.
     *
     * @param id target user ID
     * @return nullable object representing the requested user
     */
    @Nullable
    Optional<UserDetailDTO> getUser(long id);

    /**
     * Persists new user in the database and returns the generated password.
     * Note that the password returned is not encrypted (unlike the one stored in the database),
     * therefore the user should be advised to only use the password to change it.
     *
     * @param user data to be persisted
     * @return non-encrypted randomly generated password, or {@code null} if the write fails
     */
    @Nullable
    String createUser(UserDetailDTO user);

    /**
     * Updates data for existing user.
     * Note that the update fails if no object with given ID exists,
     * {@link #exists(long)} should be used to check is object exists first.
     * <p>
     * This method only updates the fields allowed for account owner.
     * <p>
     * Note: since not all fields are allowed to be updated, another DTO representing only
     * updatable field might be preferred.
     *
     * @param id   reference id to the object to be updated
     * @param user object representing updated data
     * @return updated object if write succeeds, {@code null} otherwise
     * @see #updateUserPermissions(long, UserDetailDTO) for updating fields allowed for admin.
     */
    UserDTO updateUserInfo(long id, UserDTO user);

    /**
     * Updates data for existing user.
     * Note that the update fails if no object with given ID exists,
     * {@link #exists(long)} should be used to check is object exists first.
     * <p>
     * This method only updates the fields allowed for admin user.
     * <p>
     * Note: since not all fields are allowed to be updated, another DTO representing only
     * updatable field might be preferred
     *
     * @param id   reference id to the object to be updated
     * @param user object representing updated data
     * @return updated object if write succeeds, {@code null} otherwise
     * @see #updateUserInfo(long, UserDTO) for updating fields allowed for account owner.
     */
    UserDetailDTO updateUserPermissions(long id, UserDetailDTO user);

    /**
     * Updates password for user.
     * Note that this method should only be allowed for the account owner.
     *
     * @param email    user requesting the password change
     * @param password new password to be persisted
     */
    void updatePassword(String email, String password);

    /**
     * Deletes user with requested ID.
     * Note that it is not this method's responsibility to validate the object.
     *
     * @param id identifier of the object to be deleted.
     */
    void deleteUser(long id);

    /**
     * Assigns a project to a user.
     *
     * @param userId    identifier of the assignee
     * @param projectId identifier of the project to be assigned
     * @return object representing updated data
     */
    UserDetailDTO addUserProject(long userId, long projectId);

    /**
     * Unassigns a user from a project.
     *
     * @param userId    identifier of the assignee
     * @param projectId identifier of the project to be unassigned
     * @return object representing updated data
     */
    UserDetailDTO removeUserProject(long userId, long projectId);
}
