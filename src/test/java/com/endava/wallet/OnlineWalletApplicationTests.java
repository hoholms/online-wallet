package com.endava.wallet;

import com.endava.wallet.controller.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class OnlineWalletApplicationTests {

    @Autowired
    private DashboardController dashboardController;
    @Autowired
    private LoginController loginController;
    @Autowired
    private RegisterController registerController;
    @Autowired
    private TransactionController transactionController;
    @Autowired
    private UserController userController;
    @Test
    void contextLoads() {
        assertThat(dashboardController).isNotNull();
        assertThat(loginController).isNotNull();
        assertThat(registerController).isNotNull();
        assertThat(transactionController).isNotNull();
        assertThat(userController).isNotNull();
    }

}
