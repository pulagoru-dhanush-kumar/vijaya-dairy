package com.uday.vijayadairy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayClient;
import com.uday.vijayadairy.configuration.RazorpayConfig;
import com.uday.vijayadairy.model.Cart;
import com.uday.vijayadairy.model.CartItem;
import com.uday.vijayadairy.model.Order;
import com.uday.vijayadairy.model.OrderStatus;
import com.uday.vijayadairy.model.Product;
import com.uday.vijayadairy.model.User;
import com.uday.vijayadairy.repository.OrderRepository;
import com.uday.vijayadairy.repository.UserRepository;
import com.uday.vijayadairy.util.RazorpayUtil;

@Service
public class PaymentService {

    @Autowired
    private CartService cartService;

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private RazorpayConfig razorpayConfig;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> createOrder(String email) throws Exception {
        System.out.println("DEBUG: createOrder called for email: " + email);

        Cart cart = cartService.getCart(email);

        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double total = cart.getCarttotal();
        System.out.println("DEBUG: Cart total: " + total);
        int amount = (int) (total * 100); // Razorpay uses paise
        System.out.println("DEBUG: Amount sent to Razorpay (paise): " + amount);

        JSONObject options = new JSONObject();
        options.put("amount", amount);
        options.put("currency", "INR");
        options.put("receipt", "txn_" + System.currentTimeMillis());

        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(options);
        System.out.println("DEBUG: Razorpay Order created: " + razorpayOrder);

        // ✅ Convert CartItem → Product (NO model change)
        List<Product> products = cart.getItems()
                .stream()
                .map(CartItem::getProduct)
                .collect(Collectors.toList());

        // ✅ Calculate total quantity properly
        int totalQty = cart.getItems()
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

        // ✅ Save order with proper Optional handling
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found for email: " + email);
        }

        Order order = new Order();
        order.setUser(userOptional.get());
        order.setProducts(products);
        order.setQuantity(totalQty);
        order.setStatus(OrderStatus.PENDING);
        order.setRazorpayOrderId((String) razorpayOrder.get("id"));

        orderRepository.save(order);

        Map<String, Object> response = new HashMap<>();
        response.put("orderId", razorpayOrder.get("id"));
        response.put("amount", total);
        response.put("currency", "INR");
        response.put("key", razorpayConfig.getKeyId());

        return response;
    }

    // ✅ VERIFY SIGNATURE using consistent RazorpayUtil
    public boolean verifyPayment(String orderId, String paymentId, String signature) {
        try {
            String generated = RazorpayUtil.generateSignature(orderId, paymentId, razorpayConfig.getKeySecret());
            return generated.equals(signature);
        } catch (Exception e) {
            System.err.println("Signature verification failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ✅ UPDATE SUCCESS
    public void updateOrderAfterPayment(String razorpayOrderId, String paymentId) {

        Order order = orderRepository.findByRazorpayOrderId(razorpayOrderId);

        if (order == null) {
            throw new RuntimeException("Order not found for Razorpay Order ID: " + razorpayOrderId);
        }

        // 🔒 Prevent duplicate updates
        if (order.getStatus() == OrderStatus.SUCCESS) {
            System.out.println("DEBUG: Order already marked as SUCCESS, skipping update");
            return;
        }

        order.setRazorpayPaymentId(paymentId);
        order.setStatus(OrderStatus.SUCCESS);

        orderRepository.save(order);
        System.out.println("DEBUG: Order updated to SUCCESS for Razorpay Order ID: " + razorpayOrderId);
    }

    // ✅ HANDLE FAILURE
    public void markOrderFailed(String razorpayOrderId) {

        Order order = orderRepository.findByRazorpayOrderId(razorpayOrderId);

        if (order != null && order.getStatus() != OrderStatus.SUCCESS) {
            order.setStatus(OrderStatus.FAILED);
            orderRepository.save(order);
            System.out.println("DEBUG: Order marked as FAILED for Razorpay Order ID: " + razorpayOrderId);
        }
    }
}