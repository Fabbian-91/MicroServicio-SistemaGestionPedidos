package com.example.pedidos_service.integration.client;

import com.example.pedidos_service.common.response.ApiResponse;
import com.example.pedidos_service.integration.dto.ProductoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "producto-service",
        url = "${services.producto.url}"
)
public interface ProductoFeignClient {

    @GetMapping("/api/productos/{id}")
    ApiResponse<ProductoResponseDto> obtenerProductoPorId(@PathVariable("id") Long id);
}
