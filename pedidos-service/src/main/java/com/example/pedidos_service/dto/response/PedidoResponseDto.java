package com.example.pedidos_service.dto.response;

import com.example.pedidos_service.common.enums.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDto {

    private Long id;
    private Long clienteId;
    private LocalDateTime fechaPedido;
    private EstadoPedido estado;
    private List<DetallePedidoResponseDto> detalles;
}