package com.uday.vijayadairy.controller;

import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uday.vijayadairy.model.Cart;
import com.uday.vijayadairy.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	CartService cartService;

@PostMapping("/addtocart")	
 public ResponseEntity<?> addCart(@RequestParam("productid") Long productid,   @RequestParam(defaultValue = "1") int quantity)
 {
	System.out.println("request came ");
	 String email = SecurityContextHolder.getContext().getAuthentication().getName();
	 System.out.println(email);
	Cart userCart= cartService.addItemToCart(email,productid,quantity);
	 return ResponseEntity.ok(Map.of(
     		"email",email,
             "productStatus", true,
             "cart",userCart
             
     ));
	 
 }
@PostMapping("/increase")
public ResponseEntity<?> increaseQuantity(@RequestParam("productid") Long productid)
{
	String email=SecurityContextHolder.getContext().getAuthentication().getName();
	Cart userCart=cartService.increase(email, productid);
	
	 return ResponseEntity.ok(Map.of(
	     		"email",email,
	             "productStatus", true,
	             "cart",userCart
	             
	     ));
}

@PostMapping("/decrease")
public ResponseEntity<?> decreaseQuantity(@RequestParam("productid") Long productid)
{
	String email=SecurityContextHolder.getContext().getAuthentication().getName();
	Cart userCart=cartService.decrease(email, productid);
	
	 return ResponseEntity.ok(Map.of(
	     		"email",email,
	             "productStatus", true,
	             "cart",userCart
	             
	     ));
}


@PostMapping("/removeproduct")
public ResponseEntity<?> removefromcart(@RequestParam("productid") Long productid)
{
	String email=SecurityContextHolder.getContext().getAuthentication().getName();
	Cart userCart=cartService.removeCartItem(email, productid);
	
	  return ResponseEntity.ok(Map.of(
     		"email",email,
            "productStatus", true,
          
    
            "cart",userCart
            
    ));
}

@GetMapping("/mycart")
public ResponseEntity<?> getCart()
{

		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		Cart userCart=cartService.getCart(email);
		
		  return ResponseEntity.ok(Map.of(
	     		"email",email,
	            "productStatus", true,
	          
	    
	            "cart",userCart
	            
	    ));
	
}



	
}
