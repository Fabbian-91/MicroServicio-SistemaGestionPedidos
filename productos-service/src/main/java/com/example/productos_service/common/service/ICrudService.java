package com.example.productos_service.common.service;

import java.util.List;

public interface ICrudService<C, U, R, ID> {

    R crear(C request);

    R obtenerPorId(ID id);

    List<R> listarTodos();

    R actualizar(ID id, U request);

    void eliminar(ID id);
}