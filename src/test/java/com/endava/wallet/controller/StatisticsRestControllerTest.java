package com.endava.wallet.controller;

import com.endava.wallet.entity.*;
import com.endava.wallet.service.StatisticsService;
import com.endava.wallet.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class StatisticsRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private StatisticsRestController statisticsRestController;
    @MockBean
    private StatisticsService statisticsService;

    ObjectMapper objectMapper;
    ObjectWriter objectWriter;
    List<LineStatistics> lineStatistics;
    List<CircleStatistics> circleStatistics;
    UserService userService;

    User user;
    Profile profile;
    Transaction transaction;
    Set<Transaction> transactions;
    PasswordEncoder passwordEncoder;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(statisticsRestController).build();

        objectMapper = new ObjectMapper();
        objectWriter = objectMapper.writer();
        lineStatistics = new ArrayList<>();
        circleStatistics = new ArrayList<>();

        user = new User(1L,
                "username",
                "password",
                true,
                Collections.singleton(Authority.USER));
        profile = Mockito.mock(Profile.class);
        profile.setUser(user);
        transaction = Mockito.mock(Transaction.class);
        transaction.setAmount(BigDecimal.valueOf(200));
        transactions = new LinkedHashSet<>();
        transactions.add(transaction);
        profile.setTransactions(transactions);
    }

    @Test
    @WithUserDetails("admin")
    void getLineStatistics() throws Exception {

        Mockito.when(statisticsService.findLineStatistics(user)).thenReturn(lineStatistics);

        String content = objectWriter.writeValueAsString(user);

        mockMvc.perform(formLogin().user("admin")
                        .password(passwordEncoder
                                .encode(userService.loadUserByUsername("admin")
                                        .getPassword())))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect((redirectedUrl("/")));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/statistics/line")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    @WithUserDetails("admin")
    void getCircleStatistics() throws Exception {
        Mockito.when(statisticsService.findCircleStatistics(user)).thenReturn(circleStatistics);

        String content = objectWriter.writeValueAsString(user);

        mockMvc.perform(formLogin().user("admin")
                        .password(passwordEncoder
                                .encode(userService.loadUserByUsername("admin")
                                        .getPassword())))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect((redirectedUrl("/")));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/statistics/line")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }
}