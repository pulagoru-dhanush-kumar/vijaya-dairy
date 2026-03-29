package com.uday.vijayadairy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.uday.vijayadairy.model.Order;
import com.uday.vijayadairy.model.OrderStatus;
import com.uday.vijayadairy.model.User;
import com.uday.vijayadairy.repository.OrderRepository;
import com.uday.vijayadairy.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	
	private final List<OrderStatus> VISIBLE_STATUSES = List.of(
	        OrderStatus.SUCCESS, 
	        OrderStatus.ACCEPTED, 
	        OrderStatus.PROCESSING, 
	        OrderStatus.OUTFORDELIVERY, 
	        OrderStatus.DELIVERED, 
	        OrderStatus.CANCELLED, 
	        OrderStatus.RETURNED
	    );
	
	public List<Order> getOrders(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findVisibleOrders(user, VISIBLE_STATUSES);
    }
	
public Order updateOrderStatus(String razorpayOrderId, String status) {
        
        // 1. Fetch the order using both IDs for verification
        Order order = orderRepository.findByRazorpayOrderId( razorpayOrderId);
        
        // 2. If no order is found, return null so the controller can send a 404
        OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
        
        if (order == null) {
            return null;
        }
        
        order.setStatus(newStatus);
        
        return orderRepository.save(order);
    }
	
}
