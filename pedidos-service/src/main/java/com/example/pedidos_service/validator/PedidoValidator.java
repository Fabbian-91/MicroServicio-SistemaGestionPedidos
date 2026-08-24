package com.example.pedidos_service.validator;

import com.example.pedidos_service.common.enums.EstadoPedido;
import com.example.pedidos_service.common.exception.EstadoPedidoInvalidoException;
import com.example.pedidos_service.common.exception.PedidoNoEncontradoException;
import com.example.pedidos_service.common.exception.PedidoSinDetallesException;
import com.example.pedidos_service.common.exception.PedidoYaProcesadoException;
import com.example.pedidos_service.dto.request.pedido.CreatePedidoDto;
import com.example.pedidos_service.entity.Pedido;
import com.example.pedidos_service.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoValidator {

    private final PedidoRepository pedidoRepository;

    public void validarCreacion(CreatePedidoDto request) {

        if (request.getDetalles() == null || request.getDetalles().isEmpty()) {
            throw new PedidoSinDetallesException(
                    "El pedido debe contener al menos un producto"
            );
        }
    }

    public Pedido validarExistencia(Long id) {

        return pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new PedidoNoEncontradoException(
                                "No se encontró el pedido con id: " + id
                        )
                );
    }

    public Pedido validarParaActualizacion(Long id) {

        Pedido pedido = validarExistencia(id);

        if (pedido.getEstado() == EstadoPedido.COMPLETADO) {
            throw new PedidoYaProcesadoException(
                    "No se puede actualizar un pedido completado"
            );
        }

        if (pedido.getEstado() == EstadoPedido.CANCELADO) {
            throw new PedidoYaProcesadoException(
                    "No se puede actualizar un pedido cancelado"
            );
        }

        return pedido;
    }

    public Pedido validarParaEliminacion(Long id) {

        Pedido pedido = validarExistencia(id);

        if (pedido.getEstado() == EstadoPedido.COMPLETADO) {
            throw new PedidoYaProcesadoException(
                    "No se puede cancelar un pedido completado"
            );
        }

        if (pedido.getEstado() == EstadoPedido.CANCELADO) {
            throw new PedidoYaProcesadoException(
                    "El pedido ya se encuentra cancelado"
            );
        }

        return pedido;
    }

    public Pedido validarCambioEstado(
            Long id,
            EstadoPedido nuevoEstado
    ) {

        Pedido pedido = validarExistencia(id);

        if (nuevoEstado == null) {
            throw new EstadoPedidoInvalidoException(
                    "El nuevo estado del pedido es obligatorio"
            );
        }

        EstadoPedido estadoActual = pedido.getEstado();

        if (estadoActual == nuevoEstado) {
            throw new EstadoPedidoInvalidoException(
                    "El pedido ya se encuentra en estado " + nuevoEstado
            );
        }

        switch (estadoActual) {

            case PENDIENTE ->
                    validarTransicionDesdePendiente(nuevoEstado);

            case CONFIRMADO ->
                    validarTransicionDesdeConfirmado(nuevoEstado);

            case COMPLETADO ->
                    throw new PedidoYaProcesadoException(
                            "Un pedido completado no puede cambiar de estado"
                    );

            case CANCELADO ->
                    throw new PedidoYaProcesadoException(
                            "Un pedido cancelado no puede cambiar de estado"
                    );
        }

        return pedido;
    }

    private void validarTransicionDesdePendiente(
            EstadoPedido nuevoEstado
    ) {

        if (nuevoEstado != EstadoPedido.CONFIRMADO
                && nuevoEstado != EstadoPedido.CANCELADO) {

            throw new EstadoPedidoInvalidoException(
                    "Un pedido PENDIENTE solo puede pasar a CONFIRMADO o CANCELADO"
            );
        }
    }

    private void validarTransicionDesdeConfirmado(
            EstadoPedido nuevoEstado
    ) {

        if (nuevoEstado != EstadoPedido.COMPLETADO
                && nuevoEstado != EstadoPedido.CANCELADO) {

            throw new EstadoPedidoInvalidoException(
                    "Un pedido CONFIRMADO solo puede pasar a COMPLETADO o CANCELADO"
            );
        }
    }
}