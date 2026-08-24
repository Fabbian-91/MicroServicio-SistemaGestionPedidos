package com.example.cliente_service.common.exception;

public class CorreoClienteDuplicadoException extends RuntimeException {
    public CorreoClienteDuplicadoException(String mensaje) {
        super(mensaje);
    }
}