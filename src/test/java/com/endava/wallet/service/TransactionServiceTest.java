package com.endava.wallet.service;

import com.endava.wallet.entity.Profile;
import com.endava.wallet.entity.Transaction;
import com.endava.wallet.entity.User;
import com.endava.wallet.repository.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private ProfileService profileService;

    @Test
    public void add() {
        Profile profile = Profile.builder()
                .id(1L)
                .transactions((Set<Transaction>) Mockito.mock(Set.class))
                .balance(BigDecimal.valueOf(200))
                .user(Mockito.mock(User.class))
                .email("w@w.com")
                .firstName("Ivan")
                .lastName("Zolo")
                .createdDate(Instant.now())
                .activationCode("Code")
                .build();

        Transaction transaction = Transaction.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(200))
                .transactionDate(LocalDate.now())
                .profile(profile)
                .message("message")
                .isIncome(true)
                .build();

        transactionService.add(transaction, profile);

        Mockito.verify(transactionRepository, Mockito.times(1)).save(transaction);
        Mockito.verify(profileService, Mockito.times(1)).save(profile);
    }
}