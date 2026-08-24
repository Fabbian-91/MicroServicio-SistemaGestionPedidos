package com.example.pedidos_service.common.exception;

public class PedidoSinDetallesException extends RuntimeException {
    public PedidoSinDetallesException(String message) {
        super(message);
    }
}
