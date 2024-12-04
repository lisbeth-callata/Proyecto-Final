package com.banco.transacciones.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.banco.transacciones.model.Transaction;

public interface TransactionService {

    Mono<Transaction> createDeposit(Transaction transaction);
    Mono<Transaction> createWithdrawal(Transaction transaction);
    Mono<Transaction> createTransfer(Transaction transaction);
    Mono<Transaction> createTransaction(Transaction transaction);
    Flux<Transaction> getAllTransactions();
}