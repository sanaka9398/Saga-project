package com.saga.order.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.saga.order.dto.CreateOrderRequest;
import com.saga.order.entity.Order;
import com.saga.order.entity.OrderStatus;
import com.saga.order.repository.OrderRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository=orderRepository;
	}
	public Order createOrder(CreateOrderRequest orderRequest) {
		   Instant now = Instant.now();
		   Order order = new Order();
		   
		   order.setCustomerId(orderRequest.customerId());
	        order.setProductId(orderRequest.productId());
	        order.setQuantity(orderRequest.quantity());
	        order.setAmount(orderRequest.amount());

	        order.setStatus(OrderStatus.PENDING);

	        order.setCreatedAt(now);
	        order.setUpdatedAt(now);
	        
	        return orderRepository.save(order);
		
	}
	public Order updateStatus(String orderId,OrderStatus status) {
		Order order = orderRepository
	            .findById(orderId)
	            .orElseThrow(() ->
	                    new IllegalArgumentException(
	                            "Order not found: " + orderId
	                    )
	            );
		order.setStatus(status);
	    order.setUpdatedAt(Instant.now());

	    return orderRepository.save(order);
	}
	//cancel order
	public void cancelOrder(String orderId) {
		updateStatus(orderId,OrderStatus.CANCELLED);
	}
}
