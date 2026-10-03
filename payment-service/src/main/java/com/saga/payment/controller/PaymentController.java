package com.saga.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saga.payment.dto.PaymentRequest;
import com.saga.payment.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
	private final PaymentService paymentService;
	public PaymentController(PaymentService paymentService) {
		this.paymentService=paymentService;
	}
	@PostMapping("/process")
    public ResponseEntity<Void> processPayment(
            @Valid @RequestBody PaymentRequest request) {

        paymentService.processPayment(request);

        return ResponseEntity.ok().build();
    }
	 @PostMapping("/{orderId}/refund")
	public ResponseEntity<Void> refundPayment( @PathVariable String orderId){
		  paymentService.refundPayment(orderId);

	        return ResponseEntity.ok().build();
	}
}
