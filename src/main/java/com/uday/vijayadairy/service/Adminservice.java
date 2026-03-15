package com.uday.vijayadairy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uday.vijayadairy.model.Product;
import com.uday.vijayadairy.model.ProductStatus;
import com.uday.vijayadairy.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class Adminservice {

    @Autowired
    ProductRepository repository;

    // ── ADD PRODUCT ──────────────────────────────────────────────────────────
    public Product addProduct(Product product) {
        // Default status to DRAFT if not set
        if (product.getProductStatus() == null) {
            product.setProductStatus(ProductStatus.DRAFT);
        }
        return repository.save(product);
    }

    // ── UPDATE PRODUCT ───────────────────────────────────────────────────────
    public Product updateProduct(Long pid, Product updatedProduct) {
        Product existing = repository.findById(pid)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + pid));

        // Only update fields that are provided (non-null)
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

        // Rebuild quantity if transient fields are supplied
        if (updatedProduct.getQuantityvalue() > 0 && updatedProduct.getUnit() != null) {
            existing.setQuantityvalue(updatedProduct.getQuantityvalue());
            existing.setUnit(updatedProduct.getUnit());
            existing.buildQuantity(); // triggers @PreUpdate logic manually for clarity
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

    // ── GET PRODUCTS BY STATUS (e.g. only ACTIVE for storefront) ─────────────
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
}