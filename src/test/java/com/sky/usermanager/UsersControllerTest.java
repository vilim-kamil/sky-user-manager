package com.sky.usermanager;

import com.sky.usermanager.models.common.UserRole;
import com.sky.usermanager.models.dto.ProjectDTO;
import com.sky.usermanager.models.dto.UserDTO;
import com.sky.usermanager.models.dto.UserDetailDTO;
import com.sky.usermanager.models.entity.User;
import com.sky.usermanager.services.UsersService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersControllerTest extends AbstractTest {

    @MockBean
    private UsersService usersService;

    private final UserDTO userData = new UserDTO(1, "test_email", "test_user");

    private final UserDetailDTO userDetailData = new UserDetailDTO(1,
            "test_email",
            "test_user",
            UserRole.DEFAULT,
            List.of(new ProjectDTO(1,
                    "test_project")));

    @Override
    @Before
    public void setUp() {
        Mockito.when(usersService.exists(Mockito.isA(Long.class))).thenReturn(true);
        super.setUp();
    }

    @Test
    public void testGetUsers() throws Exception {
        List<UserDTO> users = List.of(userData);
        Mockito.when(usersService.getUsers()).thenReturn(users);

        String uri = "/users";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();

        User[] usersList = super.mapFromJson(content, User[].class);
        assertEquals(users.size(), usersList.length);
    }

    @Test
    public void testGetUserById() throws Exception {
        Mockito.when(usersService.getUser(1)).thenReturn(Optional.of(userDetailData));

        String uri = "/users/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();

        User user = super.mapFromJson(content, User.class);
        assertEquals(mapToJson(userDetailData), mapToJson(user.mapToDetailDto()));
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserDTO user = new UserDTO(1, "test_email", "test_user_updated");
        Mockito.when(usersService.updateUserInfo(Mockito.isA(Long.class), Mockito.isA(UserDTO.class))).thenReturn(user);

        MockHttpServletResponse response = testPut("/users/1", mapToJson(user));
        assertEquals(200, response.getStatus());
        assertEquals(mapToJson(user), response.getContentAsString());
    }

    @Test
    public void testUpdatePassword() throws Exception {
        MockHttpServletResponse response = testPut("/users", "123456");
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testObjectNotFound() throws Exception {
        String uri = "/users/99999";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }
}
