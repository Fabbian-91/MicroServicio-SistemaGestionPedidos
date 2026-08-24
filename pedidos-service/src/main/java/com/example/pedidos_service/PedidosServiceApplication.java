package com.example.pedidos_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients //Habilitador para consumir otros servicios
public class PedidosServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PedidosServiceApplication.class, args);
	}
}
