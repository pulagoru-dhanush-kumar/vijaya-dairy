package com.uday.vijayadairy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uday.vijayadairy.model.Order;
import com.uday.vijayadairy.model.OrderStatus;
import com.uday.vijayadairy.model.Product;
import com.uday.vijayadairy.model.ProductStatus;
import com.uday.vijayadairy.repository.OrderRepository;
import com.uday.vijayadairy.repository.ProductRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Adminservice {

    @Autowired
    ProductRepository repository;

    // ── Injected for admin order queries ─────────────────────────────────────
    @Autowired
    OrderRepository orderRepository;

    // ════════════════════════════════════════════════════════════════════════
    //  PRODUCT OPERATIONS  (unchanged from original)
    // ════════════════════════════════════════════════════════════════════════

    // ── ADD PRODUCT ──────────────────────────────────────────────────────────
    public Product addProduct(Product product) {
        if (product.getProductStatus() == null) {
            product.setProductStatus(ProductStatus.DRAFT);
        }
        return repository.save(product);
    }

    // ── UPDATE PRODUCT ───────────────────────────────────────────────────────
    public Product updateProduct(Long pid, Product updatedProduct) {
        Product existing = repository.findById(pid)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + pid));

        if (updatedProduct.getName() != null)
            existing.setName(updatedProduct.getName());

        if (updatedProduct.getPrice() != null)
            existing.setPrice(updatedProduct.getPrice());

        if (updatedProduct.getImageurl() != null)
            existing.setImageurl(updatedProduct.getImageurl());

        if (updatedProduct.getCategory() != null)
            existing.setCategory(updatedProduct.getCategory());

        if (updatedProduct.getProductStatus() != null)
            existing.setProductStatus(updatedProduct.getProductStatus());

        if (updatedProduct.getAvailable_items() > 0)
            existing.setAvailable_items(updatedProduct.getAvailable_items());

        if (updatedProduct.getQuantityvalue() > 0 && updatedProduct.getUnit() != null) {
            existing.setQuantityvalue(updatedProduct.getQuantityvalue());
            existing.setUnit(updatedProduct.getUnit());
            existing.buildQuantity();
        }

        return repository.save(existing);
    }

    // ── DELETE PRODUCT ───────────────────────────────────────────────────────
    public String deleteProduct(Long pid) {
        if (!repository.existsById(pid)) {
            throw new RuntimeException("Product not found with id: " + pid);
        }
        repository.deleteById(pid);
        return "Product with id " + pid + " deleted successfully.";
    }

    // ── GET ALL PRODUCTS ─────────────────────────────────────────────────────
    public List<Product> getAllProducts() {
        return (List<Product>) repository.findAll();
    }

    // ── GET PRODUCT BY ID ────────────────────────────────────────────────────
    public Product getProductById(Long pid) {
        return repository.findById(pid)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + pid));
    }

    // ── GET PRODUCTS BY STATUS ────────────────────────────────────────────────
    public List<Product> getProductsByStatus(ProductStatus status) {
        return repository.findByProductStatus(status);
    }

    // ── PUBLISH / UNPUBLISH SHORTCUT ─────────────────────────────────────────
    public Product updateStatus(Long pid, ProductStatus status) {
        Product product = repository.findById(pid)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + pid));
        product.setProductStatus(status);
        return repository.save(product);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  ORDER OPERATIONS  (NEW — admin-only, no auth filter)
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Returns ALL orders across ALL users, sorted newest-first.
     * Used by the admin dashboard and orders page.
     */
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        // Sort newest-first so the admin sees recent activity at the top
        orders.sort(Comparator.comparing(Order::getCreated_at,
                Comparator.nullsLast(Comparator.reverseOrder())));
        return orders;
    }

    /**
     * Returns orders filtered by a specific OrderStatus.
     * Throws IllegalArgumentException if the status string is invalid.
     */
    public List<Order> getOrdersByStatus(String statusStr) {
        OrderStatus status = OrderStatus.valueOf(statusStr.toUpperCase()); // throws IAE on bad value
        return orderRepository.findAll()
                .stream()
                .filter(o -> status.equals(o.getStatus()))
                .sorted(Comparator.comparing(Order::getCreated_at,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    /**
     * Returns a single order by its primary key (admin view — no user check).
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }
}