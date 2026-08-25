package com.example.cliente_service.controller;

import com.example.cliente_service.common.controller.ICrudController;
import com.example.cliente_service.common.response.ApiResponse;
import com.example.cliente_service.dto.request.CreateClienteDto;
import com.example.cliente_service.dto.request.UpdateClienteDto;
import com.example.cliente_service.dto.response.ClienteResponseDto;
import com.example.cliente_service.service.contract.IClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clientes")
public class ClienteController implements ICrudController<
        CreateClienteDto,
        UpdateClienteDto,
        ClienteResponseDto,
        Long> {

    //Inyección de dependecias
    private final IClienteService clienteService;

    /**
     * Mjetodo para crear un cliente
     * @param request
     * @return
     */
    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<ClienteResponseDto>> crear(
            @Valid @RequestBody CreateClienteDto request
    ) {

        ClienteResponseDto cliente = clienteService.crear(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Cliente creado correctamente",
                                cliente
                        )
                );
    }

    /**
     * Metodo para obtener un cliente por su id
     * @param id
     * @return
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponseDto>> obtenerPorId(
            @PathVariable Long id
    ) {

        ClienteResponseDto cliente = clienteService.obtenerPorId(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Cliente obtenido correctamente",
                        cliente
                )
        );
    }

    /**
     * Metodo para listar todos los clientes
     * @return
     */
    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<ClienteResponseDto>>> listarTodos() {
        //Metodo demostración
        List<ClienteResponseDto> response=clienteService.listarTodos();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success("Cliente obtenidos correctamente",response));
    }

    /**
     * Metodo para actulizar un cliente
     * @param id
     * @param request
     * @return
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponseDto>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UpdateClienteDto request
    ) {

        ClienteResponseDto cliente =
                clienteService.actualizar(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Cliente actualizado correctamente",
                        cliente
                )
        );
    }

    /**
     * Metodo para eliminar un cliente
     * @param id
     * @return
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @PathVariable Long id
    ) {

        clienteService.eliminar(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Cliente eliminado correctamente",
                        null
                )
        );
    }
}