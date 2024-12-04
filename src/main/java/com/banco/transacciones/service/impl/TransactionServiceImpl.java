package com.banco.transacciones.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;
import com.banco.transacciones.model.Transaction;
import com.banco.transacciones.repository.AccountRepository;
import com.banco.transacciones.repository.TransactionRepository;
import com.banco.transacciones.service.TransactionService;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public Mono<Transaction> createDeposit(Transaction transaction) {
        // Lógica para crear un depósito
        // Buscar la cuenta asociada al depósito por el número de cuenta
        return accountRepository.findByNumeroCuenta(transaction.getCuentaOrigen())  // Buscar la cuenta
                .flatMap(account -> {
                    // Si no se encuentra la cuenta, se devuelve un error
                    if (account == null) {
                        return Mono.error(new RuntimeException("Cuenta no encontrada"));
                    }

                    // Actualizar el saldo de la cuenta (sumando el monto del depósito)
                    account.setSaldo(account.getSaldo() + transaction.getMonto());

                    // Guardar la cuenta actualizada
                    return accountRepository.save(account)
                            .flatMap(updatedAccount -> {
                                // Establecer el número de cuenta en la transacción y guardarla
                                transaction.setCuentaOrigen(updatedAccount.getNumeroCuenta());
                                return transactionRepository.save(transaction);  // Guardar la transacción
                            });
                });
    }



    @Override
    public Mono<Transaction> createWithdrawal(Transaction transaction) {
        // Lógica para crear un retiro
        return accountRepository.findByNumeroCuenta(transaction.getCuentaOrigen())
                .flatMap(account -> {
                    if (account == null) {
                        return Mono.error(new RuntimeException("Cuenta no encontrada"));
                    }

                    // Verificar si el saldo es suficiente para el retiro
                    if (account.getSaldo() < transaction.getMonto()) {
                        return Mono.error(new RuntimeException("Saldo insuficiente"));
                    }

                    // Actualizar el saldo de la cuenta (restando el monto del retiro)
                    account.setSaldo(account.getSaldo() - transaction.getMonto());

                    // Guardar la cuenta actualizada
                    return accountRepository.save(account)
                            .flatMap(updatedAccount -> {
                                // Establecer el número de cuenta en la transacción y guardarla
                                transaction.setCuentaOrigen(updatedAccount.getNumeroCuenta());
                                return transactionRepository.save(transaction);  // Guardar la transacción
                            });
                });
    }

    @Override
    public Mono<Transaction> createTransfer(Transaction transaction) {
        // Lógica para crear una transferencia
        return accountRepository.findByNumeroCuenta(transaction.getCuentaOrigen())
                .flatMap(accountOrigen -> {
                    if (accountOrigen == null) {
                        return Mono.error(new RuntimeException("Cuenta de origen no encontrada"));
                    }

                    // Buscar la cuenta de destino
                    return accountRepository.findByNumeroCuenta(transaction.getCuentaDestino())
                            .flatMap(accountDestino -> {
                                if (accountDestino == null) {
                                    return Mono.error(new RuntimeException("Cuenta de destino no encontrada"));
                                }

                                // Verificar si el saldo es suficiente para la transferencia
                                if (accountOrigen.getSaldo() < transaction.getMonto()) {
                                    return Mono.error(new RuntimeException("Saldo insuficiente"));
                                }

                                // Realizar la transferencia (restar del saldo de origen y sumar al saldo de destino)
                                accountOrigen.setSaldo(accountOrigen.getSaldo() - transaction.getMonto());
                                accountDestino.setSaldo(accountDestino.getSaldo() + transaction.getMonto());

                                // Guardar las cuentas actualizadas
                                return accountRepository.save(accountOrigen)
                                        .flatMap(updatedAccountOrigen -> {
                                            return accountRepository.save(accountDestino)
                                                    .flatMap(updatedAccountDestino -> {
                                                        // Guardar la transacción
                                                        transaction.setCuentaOrigen(updatedAccountOrigen.getNumeroCuenta());
                                                        transaction.setCuentaDestino(updatedAccountDestino.getNumeroCuenta());
                                                        return transactionRepository.save(transaction);
                                                    });
                                        });
                            });
                });
    }

    @Override
    public Mono<Transaction> createTransaction(Transaction transaction) {
        switch (transaction.getTipo()) {
            case DEPOSITO:
                return createDeposit(transaction);  // Llamamos al método que maneja los depósitos
            case RETIRO:
                return createWithdrawal(transaction);  // Llamamos al método que maneja los retiros
            case TRANSFERENCIA:
                return createTransfer(transaction);  // Llamamos al método que maneja las transferencias
            default:
                return Mono.error(new RuntimeException("Tipo de transacción desconocido"));
        }
    }

    @Override
    public Flux<Transaction> getAllTransactions() {
        // Devuelve todas las transacciones
        return transactionRepository.findAll();
    }
}