package com.example.productos_service.controller;

import com.example.productos_service.common.controller.ICrudController;
import com.example.productos_service.common.response.ApiResponse;
import com.example.productos_service.dto.request.CreateProductoDto;
import com.example.productos_service.dto.request.UpdateProductoDto;
import com.example.productos_service.dto.response.ProductoResponseDto;
import com.example.productos_service.service.contract.IProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController implements ICrudController<
        CreateProductoDto,
        UpdateProductoDto,
        ProductoResponseDto,
        Long> {

    //Inyección de dependeciar
    private final IProductoService productoService;

    /**
     * Metodo para crear un producto
     * @param request
     * @return
     */
    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<ProductoResponseDto>> crear(
            @Valid @RequestBody CreateProductoDto request
    ) {

        ProductoResponseDto producto = productoService.crear(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Producto creado correctamente",
                                producto
                        )
                );
    }

    /**
     * Metodo para obtener un solo producto
     * @param id
     * @return
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoResponseDto>> obtenerPorId(
            @PathVariable Long id
    ) {

        ProductoResponseDto producto = productoService.obtenerPorId(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Producto obtenido correctamente",
                        producto
                )
        );
    }

    /**
     * Metodo para listar todos los productos
     * @return
     */
    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductoResponseDto>>> listarTodos() {

        List<ProductoResponseDto> productos = productoService.listarTodos();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Productos obtenidos correctamente",
                        productos
                )
        );
    }

    /**
     * Metodo para actulizar un producto
     * @param id
     * @param request
     * @return
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductoResponseDto>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductoDto request
    ) {

        ProductoResponseDto producto =
                productoService.actualizar(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Producto actualizado correctamente",
                        producto
                )
        );
    }

    /**
     * Metodo para eliminar un producto
     * @param id
     * @return
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @PathVariable Long id
    ) {

        productoService.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Producto eliminado correctamente",
                        null
                )
        );
    }
}