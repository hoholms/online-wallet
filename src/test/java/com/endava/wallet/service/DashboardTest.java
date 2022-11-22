package com.endava.wallet.service;

import com.endava.wallet.controller.DashboardController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DashboardTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DashboardController dashboardController;

    //2Do Not working properly
    @Test
    public void dashboardPageTest() throws Exception {
        this.mockMvc.perform(get("/dashboard"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/div[@id='navbarSupportedContent']/div").string("e"));
    }
}
