package com.banco.transacciones.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import lombok.RequiredArgsConstructor;
import com.banco.transacciones.model.Customer;
import com.banco.transacciones.service.CustomerService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clientes")
public class CustomerController {

    private final CustomerService customerService;

    // Crear un nuevo cliente
    @PostMapping
    public Mono<Customer> createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    // Consultar todos los clientes
    @GetMapping
    public Flux<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Consultar un cliente por ID
    @GetMapping("/{id}")
    public Mono<Customer> getCustomer(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }

    // Actualizar un cliente por ID
    @PutMapping("/{id}")
    public Mono<Customer> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    // Eliminar un cliente por ID
    @DeleteMapping("/{id}")
    public Mono<Void> deleteCustomer(@PathVariable String id) {
        return customerService.deleteCustomer(id);
    }
}
