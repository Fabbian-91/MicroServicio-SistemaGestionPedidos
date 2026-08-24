package com.example.cliente_service.service.contract;

import com.example.cliente_service.common.service.ICrudService;
import com.example.cliente_service.dto.request.CreateClienteDto;
import com.example.cliente_service.dto.request.UpdateClienteDto;
import com.example.cliente_service.dto.response.ClienteResponseDto;

import java.util.List;

public interface IClienteService extends ICrudService<CreateClienteDto, UpdateClienteDto, ClienteResponseDto,Long> {
    ClienteResponseDto buscarPorCorreo(String correo);

    List<ClienteResponseDto> listarActivos();

    void cambiarEstado(Long id, boolean estado);
}
