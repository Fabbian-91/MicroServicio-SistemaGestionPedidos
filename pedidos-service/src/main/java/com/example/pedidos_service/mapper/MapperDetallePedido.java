package com.example.pedidos_service.mapper;

import com.example.pedidos_service.dto.request.detallePedido.CreateDetallePedidoDto;
import com.example.pedidos_service.dto.request.detallePedido.UpdateDetallePedidoDto;
import com.example.pedidos_service.dto.response.DetallePedidoResponseDto;
import com.example.pedidos_service.entity.DetallePedido;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MapperDetallePedido {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "precioUnitario", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    DetallePedido toEntity(CreateDetallePedidoDto dto);

    DetallePedidoResponseDto toResponse(DetallePedido detallePedido);

    @BeanMapping(
            nullValuePropertyMappingStrategy =
                    NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "precioUnitario", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    void updateEntity(
            @MappingTarget DetallePedido detallePedido,
            UpdateDetallePedidoDto dto
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "precioUnitario", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    DetallePedido toEntity(UpdateDetallePedidoDto dto);
}