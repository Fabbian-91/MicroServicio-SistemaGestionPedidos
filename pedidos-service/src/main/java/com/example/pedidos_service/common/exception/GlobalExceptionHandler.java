package com.example.pedidos_service.common.exception;

import com.example.pedidos_service.common.response.ApiResponse;
import com.example.pedidos_service.integration.exception.ClienteServiceException;
import com.example.pedidos_service.integration.exception.ProductoServiceException;
import com.example.pedidos_service.integration.exception.ServicioNoDisponibleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EstadoPedidoInvalidoException.class)
    public ResponseEntity<ApiResponse<String>> manejarEstadoPedidoInvalido(
            EstadoPedidoInvalidoException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(PedidoNoEncontradoException.class)
    public ResponseEntity<ApiResponse<String>> manejarPedidoNoEncontrado(
            PedidoNoEncontradoException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(PedidoSinDetallesException.class)
    public ResponseEntity<ApiResponse<String>> manejarPedidoSinDetalles(
            PedidoSinDetallesException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(PedidoYaProcesadoException.class)
    public ResponseEntity<ApiResponse<String>> manejarPedidoYaProcesado(
            PedidoYaProcesadoException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(ClienteServiceException.class)
    public ResponseEntity<ApiResponse<String>> manejarClienteServiceException(
            ClienteServiceException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(ProductoServiceException.class)
    public ResponseEntity<ApiResponse<String>> manejarProductoServiceException(
            ProductoServiceException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(ServicioNoDisponibleException.class)
    public ResponseEntity<ApiResponse<String>> manejarServicioNoDisponible(
            ServicioNoDisponibleException ex) {

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> manejarValidaciones(
            MethodArgumentNotValidException ex) {

        String mensaje = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(mensaje));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> manejarExcepcionGeneral(
            Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(
                        "Ocurrió un error interno en el servidor"
                ));
    }
}