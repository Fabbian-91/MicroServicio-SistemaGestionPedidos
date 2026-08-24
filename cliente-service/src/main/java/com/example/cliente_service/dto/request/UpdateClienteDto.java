package com.example.cliente_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClienteDto {

    @Size(
            min = 2,
            max = 100,
            message = "El nombre debe tener entre 2 y 100 caracteres"
    )
    private String nombre;

    @Size(
            min = 2,
            max = 100,
            message = "El primer apellido debe tener entre 2 y 100 caracteres"
    )
    private String primerApellido;

    @Size(
            min = 2,
            max = 100,
            message = "El segundo apellido debe tener entre 2 y 100 caracteres"
    )
    private String segundoApellido;

    @Email(message = "El correo no tiene un formato válido")
    @Size(
            max = 150,
            message = "El correo no puede superar los 150 caracteres"
    )
    private String correo;

    @Size(
            min = 8,
            max = 20,
            message = "El teléfono debe tener entre 8 y 20 caracteres"
    )
    @Pattern(
            regexp = "^[0-9+\\- ]*$",
            message = "El teléfono contiene caracteres no válidos"
    )
    private String telefono;

    @Size(
            max = 255,
            message = "La dirección no puede superar los 255 caracteres"
    )
    private String direccion;
}