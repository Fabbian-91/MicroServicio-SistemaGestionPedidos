package com.example.productos_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "tbProducto", uniqueConstraints = {@UniqueConstraint(columnNames = {"codigo"})})
@Getter
@Setter
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    private Long stock;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;
}
