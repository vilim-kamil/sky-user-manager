package com.sky.usermanager;

import com.sky.usermanager.models.dto.ProjectDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectsAdminControllerTest extends AbstractTest {

    private final ProjectDTO projectData = new ProjectDTO(1, "test_project");

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testCreateProject() throws Exception {
        MockHttpServletResponse response = testPost("/admin/projects", mapToJson(projectData));
        assertEquals(200, response.getStatus());
        assertEquals(mapToJson(projectData), response.getContentAsString());
    }

    @Test
    public void testManageProject() throws Exception {
        ProjectDTO project = new ProjectDTO(1, "test_project_updated");

        MockHttpServletResponse response = testPut("/admin/projects/1", mapToJson(project));
        assertEquals(200, response.getStatus());
        assertEquals(mapToJson(project), response.getContentAsString());
    }

    @Test
    public void testDeleteProject() throws Exception {
        String uri = "/admin/projects/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
