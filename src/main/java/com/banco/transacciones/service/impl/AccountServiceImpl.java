package com.banco.transacciones.service.impl;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.banco.transacciones.model.Account;
import com.banco.transacciones.repository.AccountRepository;
import com.banco.transacciones.service.AccountService;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Mono<Account> getAccount(String id) {
        return accountRepository.findById(id);
    }

    @Override
    public Mono<Account> createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Flux<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
