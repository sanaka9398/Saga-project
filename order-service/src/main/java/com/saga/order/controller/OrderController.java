package com.saga.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saga.order.dto.CreateOrderRequest;
import com.saga.order.entity.Order;
import com.saga.order.saga.OrderSagaOrchestrator;
import com.saga.order.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderService orderService;
	private final OrderSagaOrchestrator ordersagaOrchestrator;
	public OrderController(OrderService orderService,OrderSagaOrchestrator ordersagaOrchestrator) {
		this.orderService=orderService;
		this.ordersagaOrchestrator=ordersagaOrchestrator;
	}
	 @PostMapping
	    public ResponseEntity<Order> createOrder(
	            @Valid @RequestBody CreateOrderRequest request) {

	        Order order = orderService.createOrder(request);
	        Order completedOrder =ordersagaOrchestrator.execute(order);
	        
	        return ResponseEntity .status(HttpStatus.CREATED)
	                .body(completedOrder);
	    }
}
