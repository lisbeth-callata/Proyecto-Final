package com.banco.transacciones.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Customer {
    @Id
    private String id;
    private String nombre;
    private String apellido;
    private String correo;
    private String dni;


    public Customer(String dni, String nombre, String correo) {
        this.dni = dni;
        this.nombre = nombre;
        this.correo = correo;
    }
}
