package com.example.pedidos_service.common.exception;

public class EstadoPedidoInvalidoException extends RuntimeException {
    public EstadoPedidoInvalidoException(String message) {
        super(message);
    }
}
