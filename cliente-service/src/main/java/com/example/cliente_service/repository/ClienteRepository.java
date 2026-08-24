package com.example.cliente_service.repository;

import com.example.cliente_service.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    Optional<Cliente> findByCorreo(String correo);

    boolean existsByCorreo(String correo);

    List<Cliente> findByEstado(boolean estado);

    boolean existsByCorreoAndIdNot(String correo, Long id);
}
