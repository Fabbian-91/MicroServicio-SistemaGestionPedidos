package com.example.productos_service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductoDto {

    @Size(
            min = 2,
            max = 100,
            message = "El nombre debe tener entre 2 y 100 caracteres"
    )
    private String nombre;

    @Size(
            max = 255,
            message = "La descripción no puede superar los 255 caracteres"
    )
    private String descripcion;

    @DecimalMin(
            value = "0.01",
            message = "El precio debe ser mayor que 0"
    )
    private BigDecimal precio;

    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Long stock;

    @Size(
            min = 2,
            max = 50,
            message = "El código debe tener entre 2 y 50 caracteres"
    )
    private String codigo;

    private Boolean estado;
}