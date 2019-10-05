package com.open.drive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.drive.user.User;
import com.open.drive.user.UserRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private final User user = new User("5d987d8aea356a579fd676e9", "test_user");
    @Autowired
    private MockMvc mock;
    @MockBean
    private UserRepo repo;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getOnUsersShouldReturnOk() throws Exception {
        when(repo.findAll()).thenReturn(Collections.emptyList());
        mock.perform(MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getOnUserShouldReturnUser() throws Exception {
        when(repo.findById(user.getId())).thenReturn(Optional.of(user));
        mock.perform(MockMvcRequestBuilders.get("/user?id=" + user.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    public void getOnUserShouldReturnNotFoundIfEmpty() throws Exception {
        when(repo.findById(user.getId())).thenReturn(Optional.empty());
        mock.perform(MockMvcRequestBuilders.get("/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
