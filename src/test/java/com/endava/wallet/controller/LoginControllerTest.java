package com.endava.wallet.controller;

import com.endava.wallet.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WalletController walletController;

    PasswordEncoder passwordEncoder;
    UserService userService;

    @Test
    public void contextLoads() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Here you can control your expenses.")));
    }

    @Test
    public void loginTest() throws Exception {
        this.mockMvc.perform(get("/index"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect((redirectedUrl("http://localhost/login")));
    }


    @Test
    public void correctLoginTest() throws Exception {

        this.mockMvc.perform(formLogin().user("admin")
                        .password(passwordEncoder
                                .encode(userService.loadUserByUsername("admin")
                                        .getPassword())))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect((redirectedUrl("/")));
    }

    @Test
    public void failedLoginTest() throws Exception {
        this.mockMvc.perform(post("/login").param("noSuchLogin", "noSuchPassword"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
