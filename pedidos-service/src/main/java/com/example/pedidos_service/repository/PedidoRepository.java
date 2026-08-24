package com.example.pedidos_service.repository;

import com.example.pedidos_service.common.enums.EstadoPedido;
import com.example.pedidos_service.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByEstado(EstadoPedido estado);

    List<Pedido> findByClienteIdAndEstado(
            Long clienteId,
            EstadoPedido estado
    );
}