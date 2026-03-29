package com.uday.vijayadairy.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uday.vijayadairy.model.Order;
import com.uday.vijayadairy.model.Product;
import com.uday.vijayadairy.model.ProductStatus;
import com.uday.vijayadairy.service.Adminservice;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class Admincontroller {

    @Autowired
    Adminservice adminservice;

    // ── GET ALL PRODUCTS ─────────────────────────────────────────────────────
    // GET /admin/products
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = adminservice.getAllProducts();
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "count", products.size(),
                    "data", products
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    // ── GET PRODUCT BY ID ────────────────────────────────────────────────────
    // GET /admin/products/{pid}
    @GetMapping("/products/{pid}")
    public ResponseEntity<?> getProductById(@PathVariable Long pid) {
        try {
            Product product = adminservice.getProductById(pid);
            return ResponseEntity.ok(Map.of("success", true, "data", product));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    // ── GET PRODUCTS BY STATUS ───────────────────────────────────────────────
    // GET /admin/products/status/ACTIVE
    @GetMapping("/products/status/{status}")
    public ResponseEntity<?> getByStatus(@PathVariable String status) {
        try {
            ProductStatus ps = ProductStatus.valueOf(status.toUpperCase());
            List<Product> products = adminservice.getProductsByStatus(ps);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "count", products.size(),
                    "data", products
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Invalid status value: " + status));
        }
    }

    // ── ADD PRODUCT ──────────────────────────────────────────────────────────
    // POST /admin/products
    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product) {
        try {
            Product saved = adminservice.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("success", true, "message", "Product added successfully", "data", saved));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    // ── UPDATE PRODUCT ───────────────────────────────────────────────────────
    // PUT /admin/products/{pid}
    @PutMapping("/products/{pid}")
    public ResponseEntity<?> updateProduct(@PathVariable Long pid,
                                           @RequestBody Product product) {
        try {
            Product updated = adminservice.updateProduct(pid, product);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Product updated successfully",
                    "data", updated
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    // ── PATCH STATUS (publish / unpublish / draft) ───────────────────────────
    // PATCH /admin/products/{pid}/status?value=ACTIVE
    @PatchMapping("/products/{pid}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long pid,
                                          @RequestParam String value) {
        try {
            ProductStatus status = ProductStatus.valueOf(value.toUpperCase());
            Product updated = adminservice.updateStatus(pid, status);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Status updated to " + status,
                    "data", updated
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Invalid status: " + value));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    // ── DELETE PRODUCT ───────────────────────────────────────────────────────
    // DELETE /admin/products/{pid}
    @DeleteMapping("/products/{pid}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long pid) {
        try {
            String message = adminservice.deleteProduct(pid);
            return ResponseEntity.ok(Map.of("success", true, "message", message));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  ORDER MANAGEMENT ENDPOINTS (NEW — for admin panel)
    // ════════════════════════════════════════════════════════════════════════

    /**
     * GET /admin/orders/all
     * Returns every order in the system (all users, all statuses).
     * Sorted newest-first.
     */
    @GetMapping("/orders/all")
    public ResponseEntity<?> getAllOrders() {
        try {
            List<Order> orders = adminservice.getAllOrders();
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "count", orders.size(),
                    "data", orders
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    /**
     * GET /admin/orders/status/{status}
     * Returns all orders with a specific OrderStatus (e.g. PENDING, SUCCESS).
     */
    @GetMapping("/orders/status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable String status) {
        try {
            List<Order> orders = adminservice.getOrdersByStatus(status.toUpperCase());
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "count", orders.size(),
                    "data", orders
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "Invalid status: " + status));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    /**
     * GET /admin/orders/{id}
     * Returns a single order by its primary key.
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        try {
            Order order = adminservice.getOrderById(id);
            return ResponseEntity.ok(Map.of("success", true, "data", order));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}