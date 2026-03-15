package com.uday.vijayadairy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uday.vijayadairy.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long>{
	
    Optional<CartItem> findByCartIdAndProductPid(Long cartId, Long productId);
	
}
