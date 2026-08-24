package com.example.productos_service.service.impl;

import com.example.productos_service.common.exception.ProductoNoEncontradoException;
import com.example.productos_service.dto.request.CreateProductoDto;
import com.example.productos_service.dto.request.UpdateProductoDto;
import com.example.productos_service.dto.response.ProductoResponseDto;
import com.example.productos_service.entity.Producto;
import com.example.productos_service.mapper.ProductoMapper;
import com.example.productos_service.respository.ProductoRepository;
import com.example.productos_service.service.contract.IProductoService;
import com.example.productos_service.validator.ProductoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService implements IProductoService {

    //Inyección de dependecias
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final ProductoValidator productoValidator;

    /**
     * Metodo para buscar producto por codigo
     * @param codigo
     * @return
     */
    @Override
    public ProductoResponseDto buscarPorCodigo(String codigo) {

        Producto producto = productoRepository.findByCodigo(codigo)
                .orElseThrow(() ->
                        new ProductoNoEncontradoException(
                                "No se encontró un producto con el código: " + codigo
                        )
                );

        return productoMapper.toResponse(producto);
    }

    /**
     * Metodo para listar todos los productos activos
     * @return
     */
    @Override
    public List<ProductoResponseDto> listarActivos() {

        return productoRepository.findByEstado(true)
                .stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    /**
     * Metodo para cambiar el estado de un producto
     * @param id
     * @param estado
     */
    @Override
    public void cambiarEstado(Long id, boolean estado) {

        Producto producto = buscarEntidadPorId(id);

        producto.setEstado(estado);

        productoRepository.save(producto);
    }

    /**
     * Metodo para crear un producto
     * @param request
     * @return
     */
    @Override
    public ProductoResponseDto crear(CreateProductoDto request) {

        productoValidator.validarCreacion(request);

        Producto producto = productoMapper.toEntity(request);

        Producto productoGuardado = productoRepository.save(producto);

        return productoMapper.toResponse(productoGuardado);
    }

    /**
     * Metodo para obtener un producto por su id
     * @param id
     * @return
     */
    @Override
    public ProductoResponseDto obtenerPorId(Long id) {

        Producto producto = buscarEntidadPorId(id);

        return productoMapper.toResponse(producto);
    }

    /**
     * Metodo para listar todos los productos
     * @return
     */
    @Override
    public List<ProductoResponseDto> listarTodos() {

        return productoRepository.findAll()
                .stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    /**
     * Metodo para actualizar un producto
     * @param id
     * @param request
     * @return
     */
    @Override
    public ProductoResponseDto actualizar(Long id, UpdateProductoDto request) {

        Producto producto = buscarEntidadPorId(id);

        productoValidator.validarActualizacion(id, request);

        productoMapper.updateEntity(producto, request);

        Producto productoActualizado = productoRepository.save(producto);

        return productoMapper.toResponse(productoActualizado);
    }

    /**
     * Metodo para eliminar un producto
     * @param id
     */
    @Override
    public void eliminar(Long id) {

        Producto producto = buscarEntidadPorId(id);

        producto.setEstado(false);

        productoRepository.save(producto);
    }

    /**
     * Metodo para buscar un producto por id
     * @param id
     * @return
     */
    private Producto buscarEntidadPorId(Long id) {

        return productoRepository.findById(id)
                .orElseThrow(() ->
                        new ProductoNoEncontradoException(
                                "No se encontró el producto con id: " + id
                        )
                );
    }
}