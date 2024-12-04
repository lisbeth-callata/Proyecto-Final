package com.banco.transacciones.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.banco.transacciones.model.Customer;
import com.banco.transacciones.repository.CustomerRepository;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer("1", "John", "Doe", "john.doe@example.com", "12345678");
    }

    @Test
    void testCreateCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(Mono.just(customer));

        Mono<Customer> createdCustomer = customerService.createCustomer(customer);

        assertEquals("John", createdCustomer.block().getNombre());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testGetCustomerById() {
        when(customerRepository.findById("1")).thenReturn(Mono.just(customer));

        Mono<Customer> foundCustomer = customerService.getCustomerById("1");

        assertNotNull(foundCustomer.block());
        assertEquals("John", foundCustomer.block().getNombre());
        verify(customerRepository, times(1)).findById("1");
    }

    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Flux.just(customer));

        Flux<Customer> customers = customerService.getAllCustomers();

        assertNotNull(customers);
        assertTrue(customers.count().block() > 0);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testUpdateCustomer() {
        customer.setNombre("Jane");
        when(customerRepository.save(any(Customer.class))).thenReturn(Mono.just(customer));

        Mono<Customer> updatedCustomer = customerService.updateCustomer("1", customer);

        assertEquals("Jane", updatedCustomer.block().getNombre());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testDeleteCustomer() {
        when(customerRepository.deleteById("1")).thenReturn(Mono.empty());

        Mono<Void> deletedCustomer = customerService.deleteCustomer("1");

        assertDoesNotThrow(() -> deletedCustomer.block());
        verify(customerRepository, times(1)).deleteById("1");
    }
}
