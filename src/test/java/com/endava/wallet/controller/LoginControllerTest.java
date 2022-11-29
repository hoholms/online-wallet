package com.endava.wallet.controller;

import com.endava.wallet.entity.User;
import com.endava.wallet.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    User user;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private UserService userService;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        user = userService.findUserById(1L);
    }

    @Test
    public void contextLoads() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Here you can control your expenses.")));
    }

    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(get("/index"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect((redirectedUrl("http://localhost/login")));
    }


    @Test
    public void correctLoginTest() throws Exception {

        mockMvc.perform(formLogin("/login").user(user.getUsername()).password(user.getUsername()))
                .andExpect(status().is3xxRedirection())
                .andExpect((redirectedUrl("/")));
    }

    @Test
    public void failedLoginTest() throws Exception {
        mockMvc.perform(post("/login").param("noSuchLogin", "noSuchPassword"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
