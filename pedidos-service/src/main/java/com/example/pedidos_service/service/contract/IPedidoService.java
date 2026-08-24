package com.example.pedidos_service.service.contract;

import com.example.pedidos_service.common.enums.EstadoPedido;
import com.example.pedidos_service.common.service.ICrudService;
import com.example.pedidos_service.dto.request.pedido.CreatePedidoDto;
import com.example.pedidos_service.dto.request.pedido.UpdatePedidoDto;
import com.example.pedidos_service.dto.response.PedidoResponseDto;

import java.util.List;

public interface IPedidoService extends ICrudService<
        CreatePedidoDto,
        UpdatePedidoDto,
        PedidoResponseDto,
        Long
        > {

    List<PedidoResponseDto> listarPorCliente(Long clienteId);

    List<PedidoResponseDto> listarPorEstado(EstadoPedido estado);

    void cambiarEstado(Long id, EstadoPedido estado);
}
