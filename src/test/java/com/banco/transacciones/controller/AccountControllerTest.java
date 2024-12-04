package com.banco.transacciones.controller;


import com.banco.transacciones.model.Account;
import com.banco.transacciones.model.AccountType;
import com.banco.transacciones.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    public void createAccount() {
        Account account = new Account("1", "123456789", 100.0, AccountType.AHORROS, "clienteId1");

        when(accountService.createAccount(account)).thenReturn(Mono.just(account));

        Mono<Account> result = accountController.createAccount(account);

        StepVerifier.create(result)
                .expectNext(account)
                .verifyComplete();

        verify(accountService, times(1)).createAccount(account);
    }

    @Test
    public void getAccount() {
        Account account = new Account("1", "123456789", 100.0, AccountType.AHORROS, "clienteId1");

        when(accountService.getAccount("1")).thenReturn(Mono.just(account));

        Mono<Account> result = accountController.getAccount("1");

        StepVerifier.create(result)
                .expectNext(account)
                .verifyComplete();

        verify(accountService, times(1)).getAccount("1");
    }
    @Test
    public void updateAccount() {
        Account account = new Account("1", "123456789", 100.0, AccountType.AHORROS, "clienteId1");

        when(accountService.updateAccount(account)).thenReturn(Mono.just(account));

        Mono<Account> result = accountController.updateAccount("1", account);

        StepVerifier.create(result)
                .expectNext(account)
                .verifyComplete();

        verify(accountService, times(1)).updateAccount(account);
    }

    @Test
    public void getAccount_notFound() {
        when(accountService.getAccount("999")).thenReturn(Mono.empty());

        Mono<Account> result = accountController.getAccount("999");

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        verify(accountService, times(1)).getAccount("999");
    }

    @Test
    public void createAccount_withError() {
        Account account = new Account("2", "987654321", 200.0, AccountType.CORRIENTE, "clienteId2");

        when(accountService.createAccount(account)).thenReturn(Mono.error(new RuntimeException("Error en el servicio")));

        Mono<Account> result = accountController.createAccount(account);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        verify(accountService, times(1)).createAccount(account);
    }
    @Test
    public void getAllAccounts() {
        // Lista de cuentas a devolver
        Account account1 = new Account("1", "123456789", 100.0, AccountType.AHORROS, "clienteId1");
        Account account2 = new Account("2", "987654321", 200.0, AccountType.CORRIENTE, "clienteId2");

        // Simulamos que el servicio devuelve todas las cuentas
        when(accountService.getAllAccounts()).thenReturn(Flux.just(account1, account2));

        // Llamada al método del controlador
        Flux<Account> result = accountController.getAllAccounts();

        // Verificamos que las cuentas sean devueltas correctamente
        StepVerifier.create(result)
                .expectNext(account1)
                .expectNext(account2)
                .verifyComplete();

        // Verificamos que el servicio fue invocado una vez
        verify(accountService, times(1)).getAllAccounts();
    }


}