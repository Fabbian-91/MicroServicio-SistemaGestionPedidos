package com.example.productos_service.common.exception;

public class CodigoProductoDuplicadoException extends RuntimeException {
    public CodigoProductoDuplicadoException(String codigo) {
        super("Ya existe un producto registrado con el código: " + codigo);
    }
}
