package com.example.cliente_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDto {
    private Long id;

    private String nombre;

    private String primerApellido;

    private String segundoApellido;

    private String correo;

    private String telefono;

    private String direccion;

    private boolean estado;
}
