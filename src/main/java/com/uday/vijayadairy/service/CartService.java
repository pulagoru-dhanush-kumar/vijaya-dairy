package com.uday.vijayadairy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.uday.vijayadairy.model.Cart;
import com.uday.vijayadairy.model.CartItem;
import com.uday.vijayadairy.model.Product;
import com.uday.vijayadairy.model.User;
import com.uday.vijayadairy.repository.CartItemRepository;
import com.uday.vijayadairy.repository.CartRepository;
import com.uday.vijayadairy.repository.ProductRepository;
import com.uday.vijayadairy.repository.UserRepository;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    // Get or create cart
    public Cart getCart(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Cart> existingCart = cartRepository.findByUserId(user.getId());

        if (existingCart.isPresent()) {
            return existingCart.get();
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCarttotal(0.0);

        return cartRepository.save(cart);
    }


    // Calculate cart total
    private double calculateCartTotal(List<CartItem> items) {

        double total = 0.0;

        for (CartItem item : items) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }

        return total;
    }


    // Update cart total
    private void updateCart(Cart cart) {

        double total = calculateCartTotal(cart.getItems());
        cart.setCarttotal(total);

        cartRepository.save(cart);
    }


    // Add item to cart
    public Cart addItemToCart(String email, Long productId, int quantity) {

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        Cart cart = getCart(email);

        Optional<CartItem> existingItem =
                cartItemRepository.findByCartIdAndProductPid(cart.getId(), productId);

        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);

        } 
        else {

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found in your cart.Please add the item to your cart first "));

            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);

            cart.getItems().add(cartItem);

            cartItemRepository.save(cartItem);
        }

        updateCart(cart);

        return cart;
    }


    // Increase quantity
    public Cart increase(String email, Long productId) {

        Cart cart = getCart(email);

        CartItem item = cartItemRepository
                .findByCartIdAndProductPid(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        item.setQuantity(item.getQuantity() + 1);

        updateCart(cart);

        return cart;
    }



    public Cart decrease(String email, Long productId) {

        Cart cart = getCart(email);

        CartItem item = cartItemRepository
                .findByCartIdAndProductPid(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        item.setQuantity(item.getQuantity() - 1);

        if (item.getQuantity() <= 0) {

            cart.getItems().remove(item);
            cartItemRepository.delete(item);

        }

        updateCart(cart);

        return cart;
    }

    // Remove item completely
    public Cart removeCartItem(String email, Long productId) {

        Cart cart = getCart(email);

        CartItem item = cartItemRepository
                .findByCartIdAndProductPid(cart.getId(), productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found in cart"));
      
        cart.getItems().remove(item);
        cartItemRepository.delete(item);

        updateCart(cart);

        return cart;
    }
    
    
    
    
}