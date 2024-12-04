package com.banco.transacciones.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import com.banco.transacciones.model.Customer;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

    Mono<Customer> findByDni(String dni);
    Mono<Customer> findByCorreo(String correo);
}
