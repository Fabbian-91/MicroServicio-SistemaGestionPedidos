package com.example.productos_service.service.contract;

import com.example.productos_service.common.service.ICrudService;
import com.example.productos_service.dto.request.CreateProductoDto;
import com.example.productos_service.dto.request.UpdateProductoDto;
import com.example.productos_service.dto.response.ProductoResponseDto;

import java.util.List;

public interface IProductoService extends ICrudService<
        CreateProductoDto,
        UpdateProductoDto,
        ProductoResponseDto,
        Long
        > {

    ProductoResponseDto buscarPorCodigo(String codigo);

    List<ProductoResponseDto> listarActivos();

    void cambiarEstado(Long id, boolean estado);
}