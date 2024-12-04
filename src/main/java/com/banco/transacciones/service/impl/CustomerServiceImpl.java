package com.banco.transacciones.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import lombok.RequiredArgsConstructor;
import com.banco.transacciones.model.Customer;
import com.banco.transacciones.repository.CustomerRepository;
import com.banco.transacciones.service.CustomerService;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Mono<Customer> createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Mono<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public Flux<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> updateCustomer(String id, Customer customer) {
        customer.setId(id); // Aseguramos que el cliente tiene el ID correcto
        return customerRepository.save(customer);
    }

    @Override
    public Mono<Void> deleteCustomer(String id) {
        return customerRepository.deleteById(id);
    }
}
