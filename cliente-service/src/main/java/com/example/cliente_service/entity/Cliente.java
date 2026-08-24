package com.example.cliente_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "tbCliente",
        uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"correo"}
        )
    }
)

@Getter
@Setter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre",nullable = false)
    private String nombre;

    @Column(name = "primerApellido",nullable = false)
    private String primerApellido;

    @Column(name = "segundoApellido",nullable = false)
    private String segundoApellido;

    @Column(name = "correo",nullable = false,unique = true)
    private String correo;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "estado",nullable = false)
    private boolean estado=true;
}
