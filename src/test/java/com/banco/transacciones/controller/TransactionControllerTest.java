package com.banco.transacciones.controller;

import com.banco.transacciones.model.Transaction;
import com.banco.transacciones.model.TransactionType;
import com.banco.transacciones.service.TransactionService;
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
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    public void realizarDeposito() {
        Transaction transaction = new Transaction("1", TransactionType.DEPOSITO, 100.0, null, "123456789", null);

        when(transactionService.createTransaction(transaction)).thenReturn(Mono.just(transaction));

        Mono<Transaction> result = transactionController.realizarDeposito(transaction);

        StepVerifier.create(result)
                .expectNext(transaction)
                .verifyComplete();

        verify(transactionService, times(1)).createTransaction(transaction);
    }

    @Test
    public void realizarRetiro() {
        Transaction transaction = new Transaction("2", TransactionType.RETIRO, 50.0, null, "123456789", null);

        when(transactionService.createTransaction(transaction)).thenReturn(Mono.just(transaction));

        Mono<Transaction> result = transactionController.realizarRetiro(transaction);

        StepVerifier.create(result)
                .expectNext(transaction)
                .verifyComplete();

        verify(transactionService, times(1)).createTransaction(transaction);
    }

    @Test
    public void realizarTransferencia() {
        Transaction transaction = new Transaction("3", TransactionType.TRANSFERENCIA, 150.0, null, "123456789", "987654321");

        when(transactionService.createTransaction(transaction)).thenReturn(Mono.just(transaction));

        Mono<Transaction> result = transactionController.realizarTransferencia(transaction);

        StepVerifier.create(result)
                .expectNext(transaction)
                .verifyComplete();

        verify(transactionService, times(1)).createTransaction(transaction);
    }

    @Test
    public void realizarDeposito_withError() {
        Transaction transaction = new Transaction("4", TransactionType.DEPOSITO, 100.0, null, "123456789", null);

        when(transactionService.createTransaction(transaction)).thenReturn(Mono.error(new RuntimeException("Error en el servicio")));

        Mono<Transaction> result = transactionController.realizarDeposito(transaction);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        verify(transactionService, times(1)).createTransaction(transaction);
    }
    @Test
    public void getHistorial() {
        // Creamos dos transacciones de ejemplo
        Transaction transaction1 = new Transaction("1", TransactionType.DEPOSITO, 100.0, null, "123456789", null);
        Transaction transaction2 = new Transaction("2", TransactionType.RETIRO, 50.0, null, "123456789", null);

        // Simulamos que el servicio devuelve un flujo de transacciones
        when(transactionService.getAllTransactions()).thenReturn(Flux.just(transaction1, transaction2));

        // Llamada al método del controlador
        Flux<Transaction> result = transactionController.getHistorial();

        // Verificamos que el resultado contiene las transacciones esperadas
        StepVerifier.create(result)
                .expectNext(transaction1)
                .expectNext(transaction2)
                .verifyComplete();

        // Verificamos que el servicio fue invocado una vez
        verify(transactionService, times(1)).getAllTransactions();
    }


}

