package com.saga.payment.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.saga.payment.dto.PaymentRequest;
import com.saga.payment.entity.Payment;
import com.saga.payment.entity.PaymentStatus;
import com.saga.payment.exception.PaymentFailedException;
import com.saga.payment.repository.PaymentRepository;

@Service
public class PaymentService {
	
	private final PaymentRepository paymentRepository;
	
	public PaymentService(PaymentRepository paymentRepository) {
		this.paymentRepository=paymentRepository;
	}
	
	public void processPayment(PaymentRequest request) {
		var existingPayment=paymentRepository.findByOrderId(request.orderId());
		if(existingPayment.isPresent()) {
			Payment payment = existingPayment.get();
			if(payment.getStatus() ==  PaymentStatus.COMPLETED) {
				return;
			}
			if(payment.getStatus() == PaymentStatus.REFUNDED) {
				  throw new IllegalStateException( "Payment was already refunded for order: "
                          + request.orderId());
			}
		}
		if (request.amount().signum() <= 0) {
			throw new PaymentFailedException("Invalid Payment amount for order: " + request.orderId());
		}
		
		if ("FAIL_PAYMENT".equalsIgnoreCase(request.customerId())) {
		     throw new PaymentFailedException("Payment processing failed for order: "+ request.orderId());
		}
		
		Instant now=Instant.now();
		Payment payment=new Payment();
		
		payment.setOrderId(request.orderId());
		payment.setCustomerId(request.customerId());
		payment.setAmount(request.amount());
		payment.setStatus(PaymentStatus.COMPLETED);
		payment.setCreatedAt(now);
		payment.setUpdatedAt(now);
		
		paymentRepository.save(payment);
	}
	//refund Payment 
	
	public void refundPayment(String orderId) {
		Payment payment = paymentRepository.findByOrderId(orderId)
				.orElseThrow(() -> new IllegalArgumentException("payment not found for the order" + orderId));

		if (payment.getStatus() == PaymentStatus.REFUNDED) {
			return;
		}
		if (payment.getStatus() != PaymentStatus.COMPLETED) {
			throw new IllegalStateException("Only completed payment can be refunded for order: " + orderId);
		}
		payment.setStatus(PaymentStatus.REFUNDED);
		payment.setUpdatedAt(Instant.now());
		paymentRepository.save(payment);
	}
	
}
