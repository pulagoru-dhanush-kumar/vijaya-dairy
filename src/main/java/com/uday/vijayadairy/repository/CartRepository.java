package com.uday.vijayadairy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uday.vijayadairy.model.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	
	Optional<Cart> findByUserId(int userId);
	
}
