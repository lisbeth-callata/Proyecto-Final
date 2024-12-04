package com.banco.transacciones.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.banco.transacciones.model.Account;
import com.banco.transacciones.model.Transaction;
import com.banco.transacciones.model.TransactionType;
import com.banco.transacciones.repository.AccountRepository;
import com.banco.transacciones.repository.TransactionRepository;

class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    private Transaction testTransaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testTransaction = new Transaction("1", TransactionType.DEPOSITO, 100.0, null, "12345", null);
    }

    @Test
    void createDepositTest() {
        when(accountRepository.findByNumeroCuenta("12345")).thenReturn(Mono.just(new Account("12345", 500.0)));
        when(accountRepository.save(any())).thenReturn(Mono.just(new Account("12345", 600.0)));
        when(transactionRepository.save(any())).thenReturn(Mono.just(testTransaction));

        StepVerifier.create(transactionService.createDeposit(testTransaction))
                .expectNext(testTransaction)
                .verifyComplete();

        verify(accountRepository).findByNumeroCuenta("12345");
        verify(accountRepository).save(any());
        verify(transactionRepository).save(any());
    }

    @Test
    void createWithdrawalTest() {
        when(accountRepository.findByNumeroCuenta("12345")).thenReturn(Mono.just(new Account("12345", 500.0)));
        when(accountRepository.save(any())).thenReturn(Mono.just(new Account("12345", 400.0)));
        when(transactionRepository.save(any())).thenReturn(Mono.just(testTransaction));

        testTransaction.setMonto(100.0);
        testTransaction.setTipo(TransactionType.RETIRO);

        StepVerifier.create(transactionService.createWithdrawal(testTransaction))
                .expectNext(testTransaction)
                .verifyComplete();

        verify(accountRepository).findByNumeroCuenta("12345");
        verify(accountRepository).save(any());
        verify(transactionRepository).save(any());
    }

    @Test
    void createTransferTest() {
        when(accountRepository.findByNumeroCuenta("12345")).thenReturn(Mono.just(new Account("12345", 500.0)));
        when(accountRepository.findByNumeroCuenta("67890")).thenReturn(Mono.just(new Account("67890", 300.0)));
        when(accountRepository.save(any())).thenReturn(Mono.just(new Account("12345", 400.0)));
        when(transactionRepository.save(any())).thenReturn(Mono.just(testTransaction));

        testTransaction.setMonto(100.0);
        testTransaction.setTipo(TransactionType.TRANSFERENCIA);
        testTransaction.setCuentaDestino("67890");

        StepVerifier.create(transactionService.createTransfer(testTransaction))
                .expectNext(testTransaction)
                .verifyComplete();

        verify(accountRepository).findByNumeroCuenta("12345");
        verify(accountRepository).findByNumeroCuenta("67890");
        verify(accountRepository, times(2)).save(any());
        verify(transactionRepository).save(any());
    }

    @Test
    void createTransactionTest() {
        when(accountRepository.findByNumeroCuenta("12345")).thenReturn(Mono.just(new Account("12345", 500.0)));
        when(accountRepository.save(any())).thenReturn(Mono.just(new Account("12345", 600.0)));
        when(transactionRepository.save(any())).thenReturn(Mono.just(testTransaction));

        StepVerifier.create(transactionService.createTransaction(testTransaction))
                .expectNext(testTransaction)
                .verifyComplete();

        verify(accountRepository).findByNumeroCuenta("12345");
        verify(accountRepository).save(any());
        verify(transactionRepository).save(any());
    }

    @Test
    void getAllTransactionsTest() {
        when(transactionRepository.findAll()).thenReturn(Flux.just(testTransaction));

        StepVerifier.create(transactionService.getAllTransactions())
                .expectNext(testTransaction)
                .verifyComplete();

        verify(transactionRepository).findAll();
    }

    @Test
    void createWithdrawal_withInsufficientBalanceTest() {
        when(accountRepository.findByNumeroCuenta("12345")).thenReturn(Mono.just(new Account("12345", 50.0)));  // Saldo insuficiente
        testTransaction.setMonto(100.0);  // Intentamos retirar más de lo que hay en la cuenta

        StepVerifier.create(transactionService.createWithdrawal(testTransaction))
                .expectError(RuntimeException.class)  // Debería lanzar un error por saldo insuficiente
                .verify();

        verify(accountRepository).findByNumeroCuenta("12345");
    }
}