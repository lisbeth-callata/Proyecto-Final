package com.banco.transacciones.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import com.banco.transacciones.model.Account;
import com.banco.transacciones.model.AccountType;
import com.banco.transacciones.repository.AccountRepository;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void createAccount() {
        Account account = new Account("1", "123456789", 100.0, AccountType.AHORROS, "clienteId1");

        when(accountRepository.save(account)).thenReturn(Mono.just(account));

        Mono<Account> result = accountService.createAccount(account);

        StepVerifier.create(result)
                .expectNext(account)
                .verifyComplete();

        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void getAccount() {
        Account account = new Account("1", "123456789", 100.0, AccountType.AHORROS, "clienteId1");

        when(accountRepository.findById("1")).thenReturn(Mono.just(account));

        Mono<Account> result = accountService.getAccount("1");

        StepVerifier.create(result)
                .expectNext(account)
                .verifyComplete();

        verify(accountRepository, times(1)).findById("1");
    }
    @Test
    public void updateAccount() {
        Account account = new Account("1", "123456789", 100.0, AccountType.AHORROS, "clienteId1");
        Account updatedAccount = new Account("1", "123456789", 200.0, AccountType.AHORROS, "clienteId1");

        when(accountRepository.save(updatedAccount)).thenReturn(Mono.just(updatedAccount));

        Mono<Account> result = accountService.updateAccount(updatedAccount);

        StepVerifier.create(result)
                .expectNext(updatedAccount)
                .verifyComplete();

        verify(accountRepository, times(1)).save(updatedAccount);
    }

    @Test
    public void getAllAccounts() {
        Account account1 = new Account("1", "123456789", 100.0, AccountType.AHORROS, "clienteId1");
        Account account2 = new Account("2", "987654321", 200.0, AccountType.CORRIENTE, "clienteId2");

        when(accountRepository.findAll()).thenReturn(Flux.just(account1, account2));

        Flux<Account> result = accountService.getAllAccounts();

        StepVerifier.create(result)
                .expectNext(account1)
                .expectNext(account2)
                .verifyComplete();

        verify(accountRepository, times(1)).findAll();
    }

}
