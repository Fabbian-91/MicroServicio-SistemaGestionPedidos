package com.example.productos_service.validator;

import com.example.productos_service.common.exception.CodigoProductoDuplicadoException;
import com.example.productos_service.dto.request.CreateProductoDto;
import com.example.productos_service.dto.request.UpdateProductoDto;

import com.example.productos_service.respository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductoValidator {

    //Inyección de dependencia
    private final ProductoRepository productoRepository;

    /**
     * Metodo para validar creación de producto
     * @param dto
     */
    public void validarCreacion(CreateProductoDto dto) {
        validarCodigoUnico(dto.getCodigo());
    }

    /**
     * Metodo para validar actualización de producto
     * @param id
     * @param dto
     */
    public void validarActualizacion(Long id, UpdateProductoDto dto) {

        Optional.ofNullable(dto.getCodigo())
                .ifPresent(codigo ->
                        validarCodigoUnicoEnActualizacion(codigo, id)
                );
    }

    /**
     * Metodo para validar que no exista un codigo de producto ya registrado
     * @param codigo
     */
    private void validarCodigoUnico(String codigo) {

        if (productoRepository.existsByCodigo(codigo)) {
            throw new CodigoProductoDuplicadoException(codigo);
        }
    }

    /**
     * Metodo para validar que no exita un codigo duplicado al actulizar
     * @param codigo
     * @param id
     */
    private void validarCodigoUnicoEnActualizacion(String codigo, Long id) {

        if (productoRepository.existsByCodigoAndIdNot(codigo, id)) {
            throw new CodigoProductoDuplicadoException(codigo);
        }
    }
}