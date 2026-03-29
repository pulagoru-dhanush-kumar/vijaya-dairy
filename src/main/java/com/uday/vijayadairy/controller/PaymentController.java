package com.uday.vijayadairy.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uday.vijayadairy.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("/checkout")
	public ResponseEntity<?> checkout() {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			
			if (email == null || email.isEmpty()) {
				return ResponseEntity.badRequest()
					.body(Map.of("error", "User not authenticated"));
			}
			
			Map<String, Object> orderData = paymentService.createOrder(email);
			return ResponseEntity.ok(
				Map.of("success", true, "data", orderData)
			);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest()
				.body(Map.of("error", e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("error", "Failed to create order: " + e.getMessage()));
		}
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verify(@RequestBody Map<String, String> data) {
		try {
			// Validate required fields
			if (!data.containsKey("razorpay_order_id") || 
				!data.containsKey("razorpay_payment_id") || 
				!data.containsKey("razorpay_signature")) {
				return ResponseEntity.badRequest()
					.body(Map.of("error", "Missing required payment fields"));
			}
			
			boolean isValid = paymentService.verifyPayment(
				data.get("razorpay_order_id"),
				data.get("razorpay_payment_id"),
				data.get("razorpay_signature")
			);
			
			if (isValid) {
				paymentService.updateOrderAfterPayment(
					data.get("razorpay_order_id"),
					data.get("razorpay_payment_id")
				);
				return ResponseEntity.ok(
					Map.of("success", true, "message", "Payment verified successfully")
				);
			} else {
				paymentService.markOrderFailed(data.get("razorpay_order_id"));
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("success", false, "error", "Payment verification failed - Invalid signature"));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("error", "Error processing payment: " + e.getMessage()));
		}
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(Map.of("error", "Server error: " + e.getMessage()));
	}
}