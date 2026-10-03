package com.saga.order.saga;

import org.springframework.stereotype.Component;

import com.saga.order.client.InventoryClient;
import com.saga.order.client.PaymentClient;
import com.saga.order.client.ShippingClient;
import com.saga.order.entity.Order;
import com.saga.order.entity.OrderStatus;
import com.saga.order.service.OrderService;

@Component
public class OrderSagaOrchestrator {
	private final OrderService orderService;
	private final InventoryClient inventoryClient;
	private final PaymentClient paymentClient;
	private final ShippingClient shippingClient;
	public OrderSagaOrchestrator(OrderService orderService,InventoryClient inventoryClient,
			PaymentClient paymentClient,ShippingClient shippingClient) {
		this.orderService=orderService;
		this.inventoryClient=inventoryClient;
		this.paymentClient=paymentClient;
		this.shippingClient=shippingClient;
	}
	public Order execute(Order order) {
		
		  boolean inventoryReserved = false;
	      boolean paymentCompleted = false;
	      boolean shipmentCreated = false;
	      try {
	    	  inventoryClient.reserve(order.getId(),order.getProductId(),order.getQuantity());
	    	  inventoryReserved = true;
	    	  orderService.updateStatus( order.getId(), OrderStatus.INVENTORY_RESERVED);
	    	  
	    	  paymentClient.processPayment(order.getId(),order.getCustomerId(),order.getAmount());
	    	  paymentCompleted = true;
	    	  orderService.updateStatus(order.getId(),OrderStatus.PAYMENT_COMPLETED);
	    	  
	    	  shippingClient.createShipment(order.getId(),order.getCustomerId());
	          shipmentCreated = true;
	          orderService.updateStatus(order.getId(),OrderStatus.SHIPMENT_CREATED);
	          
	            return orderService.updateStatus(order.getId(),OrderStatus.CONFIRMED);
	      }catch (Exception ex) {
	    	  compensate( order,inventoryReserved, paymentCompleted,shipmentCreated);

	            throw ex;
		}
	} 
	 private void compensate(Order order,boolean inventoryReserved,boolean paymentCompleted,boolean shipmentCreated) {
	        if (shipmentCreated) {
	            shippingClient.cancel(order.getId());
	        }

	        if (paymentCompleted) {
	            paymentClient.refund(order.getId());
	        }

	        if (inventoryReserved) {
	            inventoryClient.release(
	                    order.getId(),
	                    order.getProductId(),
	                    order.getQuantity()
	            );
	        }

	        orderService.cancelOrder(order.getId());
	    }
}
