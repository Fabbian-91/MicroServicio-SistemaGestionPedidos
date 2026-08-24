package com.example.cliente_service.common.controller;

import com.example.cliente_service.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICrudController<C, U, R, ID> {

    ResponseEntity<ApiResponse<R>> crear(C request);

    ResponseEntity<ApiResponse<R>> obtenerPorId(ID id);

    ResponseEntity<ApiResponse<List<R>>> listarTodos();

    ResponseEntity<ApiResponse<R>> actualizar(ID id, U request);

    ResponseEntity<ApiResponse<Void>> eliminar(ID id);
}