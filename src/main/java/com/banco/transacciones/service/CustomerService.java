package com.banco.transacciones.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.banco.transacciones.model.Customer;

public interface CustomerService {
    Mono<Customer> createCustomer(Customer customer);
    Mono<Customer> getCustomerById(String id);
    Mono<Customer> updateCustomer(String id, Customer customer);
    Mono<Void> deleteCustomer(String id);
    Flux<Customer> getAllCustomers();
}

