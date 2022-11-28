package com.endava.wallet.service;

import com.endava.wallet.entity.*;
import com.endava.wallet.repository.ProfileRepository;
import com.endava.wallet.repository.TransactionRepository;
import com.endava.wallet.repository.TransactionsCategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
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

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private TransactionsCategoryRepository categoryRepository;

    Profile profile;
    Transaction transaction;
    TransactionsCategory category;
    User user;
    @Before
    public void setUp(){
        user = new User(1L,
                "username",
                "password",
                true,
                Collections.singleton(Authority.USER));

        profile = Profile.builder()
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

        category = new TransactionsCategory();

        transaction = Transaction.builder()
                .id(1L)
                .category(category)
                .amount(BigDecimal.valueOf(200))
                .transactionDate(LocalDate.now())
                .profile(profile)
                .message("message")
                .isIncome(true)
                .build();
    }

    @Test
    public void add() {
        transactionService.add(transaction, profile);
        Mockito.verify(transactionRepository, Mockito.times(1)).save(transaction);
        Mockito.verify(profileService, Mockito.times(1)).save(profile);
    }

    @Test
    public void saveTest(){
        Mockito.when(profileService.findProfileByUser(user))
                .thenReturn(profile);
        Mockito.when(transactionRepository.findTransactionByIdAndProfile(transaction.getId(), profile))
                .thenReturn(Optional.of(transaction));
        Mockito.when(categoryRepository.findByCategory(transaction.getCategory().toString()))
                .thenReturn(Optional.of(transaction.getCategory()));

        transactionService.save(user,
                transaction.getId(),
                "message",
                transaction.getCategory().toString(),
                BigDecimal.valueOf(Mockito.anyInt()),
                LocalDate.now().toString());

        Mockito.verify(transactionRepository, Mockito.times(1)).save(transaction);
        Mockito.verify(profileService, Mockito.times(1)).save(profile);
    }

    @Test
    public void saveFailTest(){
        Mockito.when(profileRepository.findByUser(user))
                .thenReturn(Optional.empty());
        Mockito.when(transactionRepository.findTransactionByIdAndProfile(user.getId(), profile))
                .thenReturn(Optional.empty());
        Mockito.when(categoryRepository.findByCategory(transaction.getCategory().toString()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(Exception.class, () -> transactionService.save(user,
                transaction.getId(),
                "message",
                transaction.getCategory().toString(),
                BigDecimal.valueOf(Mockito.anyInt()),
                LocalDate.now().toString()));

        Mockito.verify(transactionRepository, Mockito.times(0)).save(transaction);
        Mockito.verify(profileService, Mockito.times(0)).save(profile);
    }
}