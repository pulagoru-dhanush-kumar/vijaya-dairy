package com.uday.vijayadairy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uday.vijayadairy.model.Order;
import com.uday.vijayadairy.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	
	@GetMapping("/myorders")
	public ResponseEntity<?> orders() {
	    try {
	  
	        String email = SecurityContextHolder.getContext().getAuthentication().getName();

	    
	        List<Order> myorders = orderService.getOrders(email);

	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("success", true);
	        response.put("email", email);
	        response.put("data", myorders);
	        
	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(Map.of("success", false, "error", "Could not fetch orders: " + e.getMessage()));
	    }
	}
	
	@PostMapping("/orders/{id}")
	public ResponseEntity<?> modifyOrder(@PathVariable Long id, @RequestParam String razorpayOrderId, @RequestParam String status) {
	    
	    // Let the service handle finding and saving
	    Order updatedOrder = orderService.updateOrderStatus( razorpayOrderId, status.toUpperCase());
	    if(!status.toUpperCase().equals("RETURNED") || 
	    		!status.toUpperCase().equals("CANCELLED")
	    		) ResponseEntity.badRequest().body("Invalid status. Only RETURNED or CANCELLED allowed.");
	    
	    if (updatedOrder == null) {
	        return ResponseEntity.status(404).body("Order not found or ID mismatch");
	    }

	    return ResponseEntity.ok(updatedOrder);
	}
	
	
	
	
	
}
