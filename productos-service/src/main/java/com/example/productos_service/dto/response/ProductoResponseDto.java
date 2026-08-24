package com.example.productos_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDto {

    private Long id;

    private String nombre;

    private String descripcion;

    private BigDecimal precio;

    private Long stock;

    private String codigo;

    private Boolean estado;
}