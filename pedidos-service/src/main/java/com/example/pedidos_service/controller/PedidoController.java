package com.example.pedidos_service.controller;

import com.example.pedidos_service.common.controller.ICrudController;
import com.example.pedidos_service.common.enums.EstadoPedido;
import com.example.pedidos_service.common.response.ApiResponse;
import com.example.pedidos_service.dto.request.pedido.CreatePedidoDto;
import com.example.pedidos_service.dto.request.pedido.UpdatePedidoDto;
import com.example.pedidos_service.dto.response.PedidoResponseDto;
import com.example.pedidos_service.service.impl.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController implements ICrudController<
        CreatePedidoDto,
        UpdatePedidoDto,
        PedidoResponseDto,
        Long> {

    // Inyección de dependencias
    private final PedidoService pedidoService;

    /**
     * Metodo para crear un pedido
     *
     * @param request
     * @return
     */
    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<PedidoResponseDto>> crear(
            @Valid @RequestBody CreatePedidoDto request
    ) {

        PedidoResponseDto pedido =
                pedidoService.crear(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Pedido creado correctamente",
                                pedido
                        )
                );
    }

    /**
     * Metodo para obtener un pedido por su id
     *
     * @param id
     * @return
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoResponseDto>> obtenerPorId(
            @PathVariable Long id
    ) {

        PedidoResponseDto pedido =
                pedidoService.obtenerPorId(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Pedido obtenido correctamente",
                        pedido
                )
        );
    }

    /**
     * Metodo para listar todos los pedidos
     *
     * @return
     */
    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<PedidoResponseDto>>> listarTodos() {

        List<PedidoResponseDto> pedidos =
                pedidoService.listarTodos();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Pedidos obtenidos correctamente",
                        pedidos
                )
        );
    }

    /**
     * Metodo para actualizar un pedido
     *
     * @param id
     * @param request
     * @return
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoResponseDto>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePedidoDto request
    ) {

        PedidoResponseDto pedido =
                pedidoService.actualizar(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Pedido actualizado correctamente",
                        pedido
                )
        );
    }

    /**
     * Metodo para eliminar un pedido
     *
     * @param id
     * @return
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @PathVariable Long id
    ) {

        pedidoService.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Pedido eliminado correctamente",
                        null
                )
        );
    }

    /**
     * Metodo para listar pedidos por cliente
     *
     * @param clienteId
     * @return
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<ApiResponse<List<PedidoResponseDto>>> listarPorCliente(
            @PathVariable Long clienteId
    ) {

        List<PedidoResponseDto> pedidos =
                pedidoService.listarPorCliente(clienteId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Pedidos del cliente obtenidos correctamente",
                        pedidos
                )
        );
    }

    /**
     * Metodo para listar pedidos por estado
     *
     * @param estado
     * @return
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<ApiResponse<List<PedidoResponseDto>>> listarPorEstado(
            @PathVariable EstadoPedido estado
    ) {

        List<PedidoResponseDto> pedidos =
                pedidoService.listarPorEstado(estado);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Pedidos por estado obtenidos correctamente",
                        pedidos
                )
        );
    }

    /**
     * Metodo para cambiar el estado de un pedido
     *
     * @param id
     * @param nuevoEstado
     * @return
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<Void>> cambiarEstado(
            @PathVariable Long id,
            @RequestParam EstadoPedido nuevoEstado
    ) {

        pedidoService.cambiarEstado(id, nuevoEstado);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Estado del pedido actualizado correctamente",
                        null
                )
        );
    }
}