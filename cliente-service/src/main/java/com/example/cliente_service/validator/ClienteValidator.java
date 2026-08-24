package com.example.cliente_service.validator;

import com.example.cliente_service.common.exception.CorreoClienteDuplicadoException;
import com.example.cliente_service.dto.request.CreateClienteDto;
import com.example.cliente_service.dto.request.UpdateClienteDto;

import com.example.cliente_service.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteValidator {
    //Inyeccion de dependecias
    private final ClienteRepository clienteRepository;

    /**
     * Metodo para valida si un cliente fue creado correctamente
     * @param dto
     */
    public void validarCreacion(CreateClienteDto dto) {
        validarCorreoUnico(dto.getCorreo());
    }

    /**
     * Metodo para validar si un cliente fue actulizado correctamente
     * @param id
     * @param dto
     */
    public void validarActualizacion(Long id, UpdateClienteDto dto) {

        Optional.ofNullable(dto.getCorreo())
                .ifPresent(correo ->
                        validarCorreoUnicoEnActualizacion(correo, id)
                );
    }

    /**
     * Metodo para validar correo unico de cliente
     * @param correo
     */
    private void validarCorreoUnico(String correo) {

        if (clienteRepository.existsByCorreo(correo)) {
            throw new CorreoClienteDuplicadoException(correo);
        }
    }

    /**
     * Metodo para validar el correo en la actulización
     * @param correo
     * @param id
     */
    private void validarCorreoUnicoEnActualizacion(String correo, Long id) {

        if (clienteRepository.existsByCorreoAndIdNot(correo, id)) {
            throw new CorreoClienteDuplicadoException(correo);
        }
    }
}