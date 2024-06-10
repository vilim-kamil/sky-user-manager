package com.sky.usermanager;

import com.sky.usermanager.models.common.UserRole;
import com.sky.usermanager.models.dto.ProjectDTO;
import com.sky.usermanager.models.dto.UserDetailDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersAdminControllerTest extends AbstractTest {

    private final UserDetailDTO userDetailData = new UserDetailDTO(1,
            "test_email",
            "test_user",
            UserRole.DEFAULT,
            List.of(new ProjectDTO(1,
                    "test_project")));

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testCreateUser() throws Exception {
        MockHttpServletResponse response = testPost("/admin/users", mapToJson(userDetailData));

        assertEquals(200, response.getStatus());
    }

    @Test
    public void testManageUser() throws Exception {
        UserDetailDTO user = new UserDetailDTO(1, "test_email", "test_user_updated", UserRole.ADMIN, new ArrayList<>());

        MockHttpServletResponse response = testPut("/admin/users/1", mapToJson(user));
        assertEquals(200, response.getStatus());
        assertEquals(mapToJson(user), response.getContentAsString());
    }

    @Test
    public void testDeleteUser() throws Exception {
        String uri = "/admin/users/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testAssignProject() throws Exception {
        ProjectDTO project = new ProjectDTO(0, "test_project");
        MockHttpServletResponse response = testPut("/admin/users/1/projects", mapToJson(project));
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testDeleteProject() throws Exception {
        String uri = "/admin/users/1/projects/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .delete(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
