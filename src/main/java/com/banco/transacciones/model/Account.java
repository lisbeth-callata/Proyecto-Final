package com.banco.transacciones.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Account {

    @Id
    private String id;
    private String numeroCuenta;
    private Double saldo = 0.0;
    private AccountType tipoCuenta;
    private String clienteId;

    public Account(String numeroCuenta, Double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }
}