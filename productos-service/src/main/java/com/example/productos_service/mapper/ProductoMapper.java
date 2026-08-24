package com.example.productos_service.mapper;

import com.example.productos_service.dto.request.CreateProductoDto;
import com.example.productos_service.dto.request.UpdateProductoDto;
import com.example.productos_service.dto.response.ProductoResponseDto;
import com.example.productos_service.entity.Producto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    // Dto a entidad
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Producto toEntity(CreateProductoDto dto);


    // Entidad a dto
    ProductoResponseDto toResponse(Producto producto);


    // Update dto a entidad existente
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void updateEntity(@MappingTarget Producto producto, UpdateProductoDto dto);
}
