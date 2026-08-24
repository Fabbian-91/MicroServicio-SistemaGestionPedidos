package com.example.pedidos_service.dto.request.pedido;

import com.example.pedidos_service.common.enums.EstadoPedido;
import com.example.pedidos_service.dto.request.detallePedido.UpdateDetallePedidoDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePedidoDto {

    @NotNull(message = "El cliente es obligatorio")
    @Positive(message = "El id del cliente debe ser mayor a 0")
    private Long clienteId;

    @NotEmpty(message = "El pedido debe contener al menos un detalle")
    @Valid
    private List<UpdateDetallePedidoDto> detalles;

    @NotNull(message = "El estado es obligario")
    private EstadoPedido estado;
}