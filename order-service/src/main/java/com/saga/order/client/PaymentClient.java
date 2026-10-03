package com.saga.order.client;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.saga.order.dto.PaymentRequest;

@Component
public class PaymentClient {
	  private final RestClient restClient;
	   public PaymentClient(
	            RestClient.Builder builder,
	            @Value("${services.payment.url}") String paymentUrl) {

	        this.restClient = builder.baseUrl(paymentUrl).build();
	    }
	   public void processPayment(  String orderId, String customerId,BigDecimal amount) {
		   PaymentRequest request= new PaymentRequest(orderId, customerId, amount);
		   restClient.post().uri("/api/payments/process").body(request).retrieve().toBodilessEntity();
	   }
	   
		public void refund(String orderId) {

			restClient.post().uri("/api/payments/{orderId}/refund", orderId).retrieve().toBodilessEntity();
		}
}
