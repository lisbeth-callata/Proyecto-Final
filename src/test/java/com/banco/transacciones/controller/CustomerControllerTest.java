package com.banco.transacciones.controller;

import com.banco.transacciones.model.Customer;
import com.banco.transacciones.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    // Test para crear un cliente
    @Test
    public void createCustomer() {
        // Datos del cliente a crear
        Customer customer = new Customer("71388332", "Juan Perez", "juan.perez@example.com");

        // Simulamos que el servicio devuelve el cliente creado
        when(customerService.createCustomer(customer)).thenReturn(Mono.just(customer));

        // Llamada al método del controlador
        Mono<Customer> result = customerController.createCustomer(customer);

        // Verificamos que el resultado es el esperado
        StepVerifier.create(result)
                .expectNext(customer)
                .verifyComplete();

        // Verificamos que el servicio fue invocado una vez
        verify(customerService, times(1)).createCustomer(customer);
    }

    // Test para obtener un cliente por ID
    @Test
    public void getCustomer() {
        // Datos del cliente a consultar
        Customer customer = new Customer("1", "Juan Perez", "juan.perez@example.com");

        // Simulamos que el servicio devuelve el cliente solicitado
        when(customerService.getCustomerById("1")).thenReturn(Mono.just(customer));

        // Llamada al método del controlador
        Mono<Customer> result = customerController.getCustomer("1");

        // Verificamos que el resultado es el esperado
        StepVerifier.create(result)
                .expectNext(customer)
                .verifyComplete();

        // Verificamos que el servicio fue invocado una vez
        verify(customerService, times(1)).getCustomerById("1");
    }

    // Test para obtener todos los clientes
    @Test
    public void getAllCustomers() {
        // Lista de clientes a devolver
        Customer customer1 = new Customer("1", "Juan Perez", "juan.perez@example.com");
        Customer customer2 = new Customer("2", "Maria Lopez", "maria.lopez@example.com");

        // Simulamos que el servicio devuelve todos los clientes
        when(customerService.getAllCustomers()).thenReturn(Flux.just(customer1, customer2));

        // Llamada al método del controlador
        Flux<Customer> result = customerController.getAllCustomers();

        // Verificamos que los clientes son devueltos correctamente
        StepVerifier.create(result)
                .expectNext(customer1)
                .expectNext(customer2)
                .verifyComplete();

        // Verificamos que el servicio fue invocado una vez
        verify(customerService, times(1)).getAllCustomers();
    }

    // Test para actualizar un cliente
    @Test
    public void updateCustomer() {
        // Datos del cliente a actualizar
        Customer customer = new Customer("1", "Juan Perez", "juan.perez@example.com");

        // Simulamos que el servicio devuelve el cliente actualizado
        when(customerService.updateCustomer("1", customer)).thenReturn(Mono.just(customer));

        // Llamada al método del controlador
        Mono<Customer> result = customerController.updateCustomer("1", customer);

        // Verificamos que el resultado es el esperado
        StepVerifier.create(result)
                .expectNext(customer)
                .verifyComplete();

        // Verificamos que el servicio fue invocado una vez
        verify(customerService, times(1)).updateCustomer("1", customer);
    }

    // Test para eliminar un cliente
    @Test
    public void deleteCustomer() {
        // ID del cliente a eliminar
        String customerId = "1";

        // Simulamos que el servicio elimina correctamente el cliente
        when(customerService.deleteCustomer(customerId)).thenReturn(Mono.empty());

        // Llamada al método del controlador
        Mono<Void> result = customerController.deleteCustomer(customerId);

        // Verificamos que no haya respuesta (Mono vacío)
        StepVerifier.create(result)
                .verifyComplete();

        // Verificamos que el servicio fue invocado una vez
        verify(customerService, times(1)).deleteCustomer(customerId);
    }

    // Test para obtener un cliente que no existe
    @Test
    public void getCustomer_notFound() {
        // Simulamos que el cliente no existe
        when(customerService.getCustomerById("999")).thenReturn(Mono.empty());

        // Llamada al método del controlador
        Mono<Customer> result = customerController.getCustomer("999");

        // Verificamos que no haya respuesta (Mono vacío)
        StepVerifier.create(result)
                .verifyComplete();

        // Verificamos que el servicio fue invocado una vez
        verify(customerService, times(1)).getCustomerById("999");
    }
}
