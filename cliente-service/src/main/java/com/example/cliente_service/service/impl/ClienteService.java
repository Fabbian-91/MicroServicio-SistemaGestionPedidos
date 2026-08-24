package com.example.cliente_service.service.impl;

import com.example.cliente_service.common.exception.ClienteNoEncontradoException;
import com.example.cliente_service.dto.request.CreateClienteDto;
import com.example.cliente_service.dto.request.UpdateClienteDto;
import com.example.cliente_service.dto.response.ClienteResponseDto;
import com.example.cliente_service.entity.Cliente;

import com.example.cliente_service.mapper.ClienteMapper;
import com.example.cliente_service.repository.ClienteRepository;
import com.example.cliente_service.service.contract.IClienteService;
import com.example.cliente_service.validator.ClienteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService implements IClienteService {

    //Inyecciones de dependecias
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final ClienteValidator clienteValidator;

    /**
     * Metodo para buscar un cliente por su correo
     * @param correo
     * @return
     */
    @Override
    public ClienteResponseDto buscarPorCorreo(String correo) {

        Cliente cliente = clienteRepository.findByCorreo(correo)
                .orElseThrow(() ->
                        new ClienteNoEncontradoException(
                                "No se encontró un cliente con el correo: " + correo
                        )
                );

        return clienteMapper.toResponse(cliente);
    }

    /**
     * Metodo para listar todos los clientes activos
     * @return
     */
    @Override
    public List<ClienteResponseDto> listarActivos() {

        return clienteRepository.findByEstado(true)
                .stream()
                .map(clienteMapper::toResponse)
                .toList();
    }

    /**
     * Metodo para cambiar el estado de un cliente
     * @param id
     * @param estado
     */
    @Override
    public void cambiarEstado(Long id, boolean estado) {

        Cliente cliente = buscarEntidadPorId(id);

        cliente.setEstado(estado);

        clienteRepository.save(cliente);
    }

    /**
     * Metodo para crear un cliente
     * @param request
     * @return
     */
    @Override
    public ClienteResponseDto crear(CreateClienteDto request) {

        clienteValidator.validarCreacion(request);

        Cliente cliente = clienteMapper.toEntity(request);

        Cliente clienteGuardado = clienteRepository.save(cliente);

        return clienteMapper.toResponse(clienteGuardado);
    }

    /**
     * Metodo para obtener un cliente por su id
     * @param id
     * @return
     */
    @Override
    public ClienteResponseDto obtenerPorId(Long id) {

        Cliente cliente = buscarEntidadPorId(id);

        return clienteMapper.toResponse(cliente);
    }

    /**
     * Metodo para listar todos los clientes
     * @return
     */
    @Override
    public List<ClienteResponseDto> listarTodos() {

        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toResponse)
                .toList();
    }

    /**
     * Metodo para actulizar un cliente
     * @param id
     * @param request
     * @return
     */
    @Override
    public ClienteResponseDto actualizar(Long id, UpdateClienteDto request) {

        Cliente cliente = buscarEntidadPorId(id);

        clienteValidator.validarActualizacion(id, request);

        clienteMapper.updateEntity(cliente, request);

        Cliente clienteActualizado = clienteRepository.save(cliente);

        return clienteMapper.toResponse(clienteActualizado);
    }

    /**
     * Metodo para eliminar un cliente
     * @param id
     */
    @Override
    public void eliminar(Long id) {

        Cliente cliente = buscarEntidadPorId(id);

        cliente.setEstado(false);

        clienteRepository.save(cliente);
    }

    /**
     * Metodo para eliminar un cliente
     * @param id
     * @return
     */
    private Cliente buscarEntidadPorId(Long id) {

        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ClienteNoEncontradoException(
                                "No se encontró el cliente con id: " + id
                        )
                );
    }
}