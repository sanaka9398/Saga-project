package com.saga.order.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.saga.order.dto.InventoryRequest;

@Component
public class InventoryClient {
	private final RestClient restClient;
	
	public InventoryClient(RestClient.Builder builder, @Value("${services.inventory.url}") String inventoryUrl) {
		this.restClient = builder.baseUrl(inventoryUrl).build();
	}
	
	public void reserve(String orderId,String productId,int quamtity) {
		InventoryRequest request=new InventoryRequest(orderId, productId, quamtity);
		restClient.post().uri("/api/inventory/reserve").body(request).retrieve().toBodilessEntity();
	}
	public void release(String orderId,String productId,int quantity) {
		InventoryRequest request =new InventoryRequest(orderId,productId,quantity);
		 restClient.post().uri("/api/inventory/release").body(request).retrieve().toBodilessEntity();
	}
	
}
