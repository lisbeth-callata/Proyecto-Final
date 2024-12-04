package com.banco.transacciones.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import com.banco.transacciones.model.Account;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
    Mono<Account> findByNumeroCuenta(String numeroCuenta);
}
