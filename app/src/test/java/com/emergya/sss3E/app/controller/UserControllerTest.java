package com.emergya.sss3E.app.controller;

import com.emergya.sss3E.app.businesslogic.UserBusinessLogic;
import com.emergya.sss3E.app.configuration.AppConfig;
import com.emergya.sss3E.app.configuration.RestWebConfig;
import com.emergya.sss3E.app.model.User;
import com.emergya.sss3E.app.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, RestWebConfig.class })
@WebAppConfiguration
@ActiveProfiles("test")
//@SqlGroup({ @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/beforeTestRun.sql") })
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserBusinessLogic userBusinessLogic;

    private UserController userController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        userController = new UserController(userBusinessLogic);

        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        //this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        // MockitoAnnotations.initMocks(this);
        //   Mockito.reset(userRepositoryMock);

    }

    @Test
    public void get_all_users_success() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        PageRequest pageRequest = new PageRequest(1, 10);
        Page<User> users = new PageImpl<>(Arrays.asList(user1, user2), pageRequest, 2);

        when(userRepositoryMock.findAll(any(PageRequest.class))).thenReturn(users);

        mockMvc.perform(get("/api/user")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(2))).andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].username", is("user1"))).andExpect(jsonPath("$.content[1].id", is(2)))
                .andExpect(jsonPath("$.content[1].username", is("user2")));

        verify(userRepositoryMock, times(1)).findAll(any(PageRequest.class));
        verifyNoMoreInteractions(userRepositoryMock);
    }
}