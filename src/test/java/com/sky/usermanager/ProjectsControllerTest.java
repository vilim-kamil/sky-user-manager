package com.sky.usermanager;

import com.sky.usermanager.models.dto.ProjectDTO;
import com.sky.usermanager.models.dto.ProjectDetailDTO;
import com.sky.usermanager.models.dto.UserDTO;
import com.sky.usermanager.models.entity.Project;
import com.sky.usermanager.models.entity.User;
import com.sky.usermanager.services.ProjectsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectsControllerTest extends AbstractTest {

    @MockBean
    private ProjectsService projectsService;

    private final ProjectDTO projectData = new ProjectDTO(1, "test_project");

    private final ProjectDetailDTO projectDetailData = new ProjectDetailDTO(1,
            "test_project",
            List.of(new UserDTO(1,
                    "test_email",
                    "test_user")));

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testGetProjects() throws Exception {
        List<ProjectDTO> projects = List.of(projectData);
        Mockito.when(projectsService.getProjects()).thenReturn(projects);

        String uri = "/projects";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();

        User[] usersList = super.mapFromJson(content, User[].class);
        assertEquals(projects.size(), usersList.length);
    }

    @Test
    public void testGetUserById() throws Exception {
        Mockito.when(projectsService.getProject(1)).thenReturn(Optional.of(projectDetailData));

        String uri = "/projects/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();

        Project project = super.mapFromJson(content, Project.class);
        assertEquals(mapToJson(projectDetailData), mapToJson(project.mapToDetailDto()));
    }

    @Test
    public void testObjectNotFound() throws Exception {
        String uri = "/projects/99999";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }
}
