package com.example.cliente_service.mapper;

import com.example.cliente_service.dto.request.CreateClienteDto;
import com.example.cliente_service.dto.request.UpdateClienteDto;
import com.example.cliente_service.dto.response.ClienteResponseDto;
import com.example.cliente_service.entity.Cliente;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    //Combetir createDto a entidad
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Cliente toEntity(CreateClienteDto dto);

    //Combertir entidad clienteResponse
    ClienteResponseDto toResponse(Cliente cliente);

    //No sobrescribir propiedades que vienen nulas
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    void updateEntity(
            @MappingTarget Cliente cliente,
            UpdateClienteDto dto
    );
}
