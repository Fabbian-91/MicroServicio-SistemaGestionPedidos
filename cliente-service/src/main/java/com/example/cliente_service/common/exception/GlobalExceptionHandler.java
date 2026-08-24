package com.example.cliente_service.common.exception;

import com.example.cliente_service.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Metodo reponse para manejar correos duplicados
     * @param ex
     * @return
     */
    @ExceptionHandler(CorreoClienteDuplicadoException.class)
    public ResponseEntity<ApiResponse<String>> manejarCorreoDuplicado(
            CorreoClienteDuplicadoException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage()));
    }

    /**
     * Metodo response para manejar cuando un cliente no existe
     * @param ex
     * @return
     */
    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<ApiResponse<String>> manejarClientesNoEncontrados(ClienteNoEncontradoException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> manejarValidaciones(
            MethodArgumentNotValidException ex) {

        String mensaje = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(mensaje);
    }
}
