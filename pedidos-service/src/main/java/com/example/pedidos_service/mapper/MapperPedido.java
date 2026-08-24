package com.example.pedidos_service.mapper;


import com.example.pedidos_service.dto.request.pedido.CreatePedidoDto;
import com.example.pedidos_service.dto.request.pedido.UpdatePedidoDto;
import com.example.pedidos_service.dto.response.PedidoResponseDto;
import com.example.pedidos_service.entity.Pedido;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MapperPedido {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaPedido", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    Pedido toEntity(CreatePedidoDto dto);

    @Mapping(source = "fechaPedido", target = "fechaPedido")
    PedidoResponseDto toResponse(Pedido pedido);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaPedido", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    void updateEntity(
            @MappingTarget Pedido pedido,
            UpdatePedidoDto dto
    );


}