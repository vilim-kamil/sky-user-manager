package com.sky.usermanager.services;

import com.sky.usermanager.models.dto.ProjectDTO;
import com.sky.usermanager.models.dto.ProjectDetailDTO;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Handler for retrieval and persistence of users data.
 * Since both base and admin endpoints process the data in
 * a similar fashion, a single service is used - if scalability would
 * be a concern, this would need to be refactored.
 *
 * @see com.sky.usermanager.dao.ProjectsRepository
 * @see com.sky.usermanager.models.entity.Project
 * @see com.sky.usermanager.models.dto.ProjectDetailDTO
 */
public interface ProjectsService {

    /**
     * Checks if an object with the supplied ID exists.
     * This method should not query the object, since it is supposed
     * to serve as a more efficient way to check for existence than query
     * and check for nullability.
     *
     * @param id ID of the required object
     * @return {@code true} if object exists in the database, {@code false} otherwise
     */
    boolean exists(long id);

    /**
     * Queries a list of all projects persisted within the database.
     *
     * @return list of all persisted projects
     */
    List<ProjectDTO> getProjects();

    /**
     * Queries single project based on the ID field.
     * Since the result is nullable, it is recommended to check for its existence with {@link #exists(long)}.
     *
     * @param id target project ID
     * @return nullable object representing the requested project
     */
    @Nullable
    Optional<ProjectDetailDTO> getProject(long id);

    /**
     * Persists new project in the database and returns the created object.
     *
     * @param project data to be persisted
     * @return representation of the newly created data, or {@code null} if the write fails
     */
    @Nullable
    ProjectDTO createProject(ProjectDTO project);

    /**
     * Updates data for existing project.
     * Note that the update fails if no object with given ID exists,
     * {@link #exists(long)} should be used to check is object exists first.
     * <p>
     * Note: since not all fields are allowed to be updated, another DTO representing only
     * updatable field might be preferred
     *
     * @param id      reference id to the object to be updated
     * @param project object representing updated data
     * @return updated object if write succeeds, {@code null} otherwise
     */
    ProjectDTO updateProject(long id, ProjectDTO project);

    /**
     * Deletes project with requested ID.
     * Note that it is not this method's responsibility to validate the object.
     *
     * @param id identifier of the object to be deleted.
     */
    void deleteProject(long id);
}
