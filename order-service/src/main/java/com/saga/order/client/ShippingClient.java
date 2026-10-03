package com.saga.order.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.saga.order.dto.ShipmentRequest;

@Component
public class ShippingClient {
	private final RestClient restClient;
	public ShippingClient(RestClient.Builder builder,@Value("${services.shipping.url}") String shippingUrl) {
		this.restClient=builder.baseUrl(shippingUrl).build();
	}
	public void createShipment(String orderId,String customerId) {
		ShipmentRequest shipmentRequest=new ShipmentRequest(orderId, customerId);
		restClient.post().uri("/api/shipments").body(shipmentRequest).retrieve().toBodilessEntity();
	}
	public void cancel(String orderId) {
        restClient.post().uri("/api/shipments/{orderId}/cancel", orderId).retrieve().toBodilessEntity();
    }
}
