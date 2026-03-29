package com.uday.vijayadairy.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uday.vijayadairy.configuration.RazorpayConfig;
import com.uday.vijayadairy.util.RazorpayUtil;

@RestController
@RequestMapping("/test")
public class PaymentTestController {

    @Autowired
    private RazorpayConfig razorpayConfig;

    /**
     * Test endpoint to generate and verify Razorpay signatures
     * Request body should contain:
     * {
     *   "orderId": "order_xxxxx",
     *   "paymentId": "pay_xxxxx"
     * }
     */
    @PostMapping("/test-signature")
    public ResponseEntity<?> generateSignature(@RequestBody Map<String, String> data) {
        try {
            // Validate input
            String orderId = data.get("orderId");
            String paymentId = data.get("paymentId");

            if (orderId == null || orderId.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "orderId is required"));
            }
            if (paymentId == null || paymentId.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "paymentId is required"));
            }

            // Generate signature dynamically
            String signature = RazorpayUtil.generateSignature(
                orderId, 
                paymentId, 
                razorpayConfig.getKeySecret()
            );

            // Return result
            Map<String, String> response = new HashMap<>();
            response.put("razorpay_order_id", orderId);
            response.put("razorpay_payment_id", paymentId);
            response.put("razorpay_signature", signature);
            response.put("message", "Test signature generated successfully");

            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Failed to generate signature: " + e.getMessage()));
        }
    }
  
    
}