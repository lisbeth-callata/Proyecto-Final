package com.banco.transacciones.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.banco.transacciones.model.Transaction;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    // Agregar consultas personalizadas si es necesario
}
