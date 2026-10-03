package com.saga.payment.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.saga.payment.entity.Payment;

public interface PaymentRepository extends MongoRepository<Payment, String>{
	Optional<Payment> findByOrderId(String orderId);
}
