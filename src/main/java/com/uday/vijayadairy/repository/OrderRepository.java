package com.uday.vijayadairy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.uday.vijayadairy.model.Order;
import com.uday.vijayadairy.model.OrderStatus;
import com.uday.vijayadairy.model.User;

public interface OrderRepository extends JpaRepository<Order, Long>{
	  Order findByRazorpayOrderId(String razorpayOrderId);
	  
	  @Query("SELECT o FROM orders o WHERE o.user = :user AND o.status IN :statuses ORDER BY o.created_at DESC")
	    List<Order> findVisibleOrders(@Param("user") User user, @Param("statuses") List<OrderStatus> statuses);
	 
}
