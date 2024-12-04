package com.banco.transacciones.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;
import com.banco.transacciones.model.Account;
import com.banco.transacciones.service.AccountService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cuentas")
public class AccountController {

    private final AccountService accountService;

    // Crear una nueva cuenta
    @PostMapping
    public Mono<Account> createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    // Consultar una cuenta por su ID
    @GetMapping("/{id}")
    public Mono<Account> getAccount(@PathVariable String id) {
        return accountService.getAccount(id);
    }

    // Actualizar una cuenta existente
    @PutMapping("/{id}")
    public Mono<Account> updateAccount(@PathVariable String id, @RequestBody Account account) {
        account.setId(id); // Asegurarse de que la cuenta tiene el mismo ID
        return accountService.updateAccount(account);
    }

    // Consultar todas las cuentas
    @GetMapping
    public Flux<Account> getAllAccounts() {

        return accountService.getAllAccounts();
    }
}
