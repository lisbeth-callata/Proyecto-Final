package com.banco.transacciones.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;
import com.banco.transacciones.model.Transaction;
import com.banco.transacciones.model.TransactionType;
import com.banco.transacciones.service.TransactionService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transacciones")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposito")
    public Mono<Transaction> realizarDeposito(@RequestBody Transaction transaction) {
        transaction.setTipo(TransactionType.DEPOSITO);
        return transactionService.createTransaction(transaction);
    }

    @PostMapping("/retiro")
    public Mono<Transaction> realizarRetiro(@RequestBody Transaction transaction) {
        transaction.setTipo(TransactionType.RETIRO);
        return transactionService.createTransaction(transaction);
    }

    @PostMapping("/transferencia")
    public Mono<Transaction> realizarTransferencia(@RequestBody Transaction transaction) {
        transaction.setTipo(TransactionType.TRANSFERENCIA);
        return transactionService.createTransaction(transaction);
    }

    @GetMapping("/historial")
    public Flux<Transaction> getHistorial() {

        return transactionService.getAllTransactions();
    }
}