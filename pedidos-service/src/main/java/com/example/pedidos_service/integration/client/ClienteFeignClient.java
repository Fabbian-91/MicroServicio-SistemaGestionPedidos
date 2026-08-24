package com.example.pedidos_service.integration.client;

import com.example.pedidos_service.common.response.ApiResponse;
import com.example.pedidos_service.integration.dto.ClienteResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "cliente-service",
        url = "${services.cliente.url}"
)
public interface ClienteFeignClient {

    @GetMapping("/api/clientes/{id}")
    ApiResponse<ClienteResponseDto> obtenerClientePorId(@PathVariable("id") Long id);
}
