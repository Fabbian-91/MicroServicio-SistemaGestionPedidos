package com.example.pedidos_service.service.impl;

import com.example.pedidos_service.common.enums.EstadoPedido;
import com.example.pedidos_service.common.response.ApiResponse;
import com.example.pedidos_service.dto.request.detallePedido.CreateDetallePedidoDto;
import com.example.pedidos_service.dto.request.detallePedido.UpdateDetallePedidoDto;
import com.example.pedidos_service.dto.request.pedido.CreatePedidoDto;
import com.example.pedidos_service.dto.request.pedido.UpdatePedidoDto;
import com.example.pedidos_service.dto.response.PedidoResponseDto;
import com.example.pedidos_service.entity.DetallePedido;
import com.example.pedidos_service.entity.Pedido;
import com.example.pedidos_service.integration.client.ClienteFeignClient;
import com.example.pedidos_service.integration.client.ProductoFeignClient;
import com.example.pedidos_service.integration.dto.ClienteResponseDto;
import com.example.pedidos_service.integration.dto.ProductoResponseDto;
import com.example.pedidos_service.integration.exception.ClienteServiceException;
import com.example.pedidos_service.integration.exception.ProductoServiceException;
import com.example.pedidos_service.integration.exception.ServicioNoDisponibleException;
import com.example.pedidos_service.mapper.MapperDetallePedido;
import com.example.pedidos_service.mapper.MapperPedido;
import com.example.pedidos_service.repository.PedidoRepository;
import com.example.pedidos_service.service.contract.IPedidoService;
import com.example.pedidos_service.validator.PedidoValidator;
import feign.FeignException;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService implements IPedidoService {

    //Inyección de dependecias
    private final PedidoRepository pedidoRepository;
    private final MapperPedido mapperPedido;
    private final MapperDetallePedido mapperDetallePedido;
    private final PedidoValidator pedidoValidator;
    private final ClienteFeignClient clienteFeignClient;
    private final ProductoFeignClient productoFeignClient;


    /**
     * Metodo para crear un pedido
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PedidoResponseDto crear(CreatePedidoDto request) {

        // Valida reglas propias del pedido.
        pedidoValidator.validarCreacion(request);

        // El cliente pertenece a otro microservicio.
        ClienteResponseDto cliente =
                obtenerCliente(request.getClienteId());

        // No se debe crear un pedido para un cliente inactivo.
        validarClienteActivo(cliente);

        Pedido pedido = mapperPedido.toEntity(request);

        BigDecimal totalPedido = BigDecimal.ZERO;

        for (CreateDetallePedidoDto detalleDto : request.getDetalles()) {

            // El producto pertenece a productos-service.
            ProductoResponseDto producto =
                    obtenerProducto(detalleDto.getProductoId());

            // Verifica que pueda formar parte del pedido.
            validarProductoParaPedido(
                    producto,
                    detalleDto.getCantidad().longValue()
            );

            DetallePedido detalle =
                    mapperDetallePedido.toEntity(detalleDto);

            // Mantiene correctamente la relación JPA.
            detalle.setPedido(pedido);

            // El precio válido viene de productos-service.
            detalle.setPrecioUnitario(producto.getPrecio());

            BigDecimal subtotal =
                    producto.getPrecio()
                            .multiply(
                                    BigDecimal.valueOf(
                                            detalleDto.getCantidad()
                                    )
                            );

            detalle.setSubtotal(subtotal);

            // Cascade.ALL permitirá guardar el detalle con el pedido.
            pedido.getDetalles().add(detalle);

            totalPedido = totalPedido.add(subtotal);
        }

        // El total se calcula en backend, no viene del cliente.
        pedido.setTotal(totalPedido);

        // Guarda pedido y detalles en una sola transacción local.
        Pedido pedidoGuardado =
                pedidoRepository.save(pedido);

        return mapperPedido.toResponse(pedidoGuardado);
    }


    /**
     * Metodo para obtener un pedido por su id
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public PedidoResponseDto obtenerPorId(Long id) {

        // El validator centraliza la validación de existencia.
        Pedido pedido =
                pedidoValidator.validarExistencia(id);

        return mapperPedido.toResponse(pedido);
    }


    /**
     * Metodo para listar todos los pedidos
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDto> listarTodos() {

        return pedidoRepository.findAll()
                .stream()
                .map(mapperPedido::toResponse)
                .toList();
    }

    /**
     * Metodo para actuaizar un pedido
     * @param id
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PedidoResponseDto actualizar(
            Long id,
            UpdatePedidoDto request
    ) {

        // Solo permite actualizar pedidos que aún pueden modificarse.
        Pedido pedido =
                pedidoValidator.validarParaActualizacion(id);

        // Ignora null y campos controlados por el backend.
        mapperPedido.updateEntity(pedido, request);

        if (request.getClienteId() != null) {

            // Si cambia el cliente, se valida nuevamente.
            ClienteResponseDto cliente =
                    obtenerCliente(request.getClienteId());

            validarClienteActivo(cliente);
        }

        if (request.getDetalles() != null) {

            // orphanRemoval elimina los detalles anteriores.
            pedido.getDetalles().clear();

            BigDecimal nuevoTotal = BigDecimal.ZERO;

            for (UpdateDetallePedidoDto detallePedidoDto
                    : request.getDetalles()) {

                ProductoResponseDto producto =
                        obtenerProducto(
                                detallePedidoDto.getProductoId()
                        );

                validarProductoParaPedido(
                        producto,
                        detallePedidoDto.getCantidad().longValue()
                );

                DetallePedido detalle =
                        mapperDetallePedido.toEntity(detallePedidoDto);

                // Se debe asignar el dueño de la relación.
                detalle.setPedido(pedido);

                detalle.setPrecioUnitario(
                        producto.getPrecio()
                );

                BigDecimal subtotal =
                        producto.getPrecio()
                                .multiply(
                                        BigDecimal.valueOf(
                                                detallePedidoDto.getCantidad()
                                        )
                                );

                detalle.setSubtotal(subtotal);

                pedido.getDetalles().add(detalle);

                nuevoTotal =
                        nuevoTotal.add(subtotal);
            }

            // Recalcula el total según los nuevos detalles.
            pedido.setTotal(nuevoTotal);
        }

        Pedido pedidoActualizado =
                pedidoRepository.save(pedido);

        return mapperPedido.toResponse(pedidoActualizado);
    }

    /**
     * Metodo para eliminar un pedido
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void eliminar(Long id) {

        // Un pedido histórico no se elimina físicamente.
        Pedido pedido =
                pedidoValidator.validarParaEliminacion(id);

        pedido.setEstado(EstadoPedido.CANCELADO);

        pedidoRepository.save(pedido);
    }


    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDto> listarPorCliente(
            Long clienteId
    ) {

        return pedidoRepository.findByClienteId(clienteId)
                .stream()
                .map(mapperPedido::toResponse)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponseDto> listarPorEstado(
            EstadoPedido estado
    ) {

        return pedidoRepository.findByEstado(estado)
                .stream()
                .map(mapperPedido::toResponse)
                .toList();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cambiarEstado(
            Long id,
            EstadoPedido nuevoEstado
    ) {

        // El validator controla las transiciones permitidas.
        Pedido pedido =
                pedidoValidator.validarCambioEstado(
                        id,
                        nuevoEstado
                );

        pedido.setEstado(nuevoEstado);

        pedidoRepository.save(pedido);
    }


    private ClienteResponseDto obtenerCliente(Long clienteId) {

        try {

            ApiResponse<ClienteResponseDto> response =
                    clienteFeignClient.obtenerClientePorId(clienteId);

            return response.getData();
        } catch (RetryableException ex) {

            // Normalmente indica que el servicio no respondió.
            throw new ServicioNoDisponibleException(
                    "El servicio de clientes no se encuentra disponible"
            );

        } catch (FeignException ex) {

            // Traduce el error externo a una excepción de integración.
            throw new ClienteServiceException(
                    "No fue posible consultar el cliente con id: "
                            + clienteId
            );
        }
    }


    private ProductoResponseDto obtenerProducto(Long productoId) {

        try {

            ApiResponse<ProductoResponseDto> response =productoFeignClient.
                    obtenerProductoPorId(productoId);

            return response.getData();
        } catch (RetryableException ex) {

            // Evita filtrar detalles internos de Feign.
            throw new ServicioNoDisponibleException(
                    "El servicio de productos no se encuentra disponible"
            );

        } catch (FeignException ex) {

            throw new ProductoServiceException(
                    "No fue posible consultar el producto con id: "
                            + productoId
            );
        }
    }


    private void validarClienteActivo(
            ClienteResponseDto cliente
    ) {

        if (!Boolean.TRUE.equals(cliente.isEstado())) {

            throw new ClienteServiceException(
                    "El cliente se encuentra inactivo"
            );
        }
    }


    private void validarProductoParaPedido(
            ProductoResponseDto producto,
            Long cantidad
    ) {

        if (!Boolean.TRUE.equals(producto.getEstado())) {

            throw new ProductoServiceException(
                    "El producto con id "
                            + producto.getId()
                            + " se encuentra inactivo"
            );
        }

        if (producto.getStock() < cantidad) {

            throw new ProductoServiceException(
                    "El producto con id "
                            + producto.getId()
                            + " no tiene stock suficiente"
            );
        }
    }
}