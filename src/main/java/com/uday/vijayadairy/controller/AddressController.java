package com.uday.vijayadairy.controller;

import com.uday.vijayadairy.model.Address;
import com.uday.vijayadairy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Save or Update address for the CURRENT logged-in user
    @PostMapping("/my-address")
    public ResponseEntity<?> addAddress(@RequestBody Address address) {
        // Get email from Spring Security Context
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        try {
            Address savedAddress = addressService.saveAddressByEmail(email, address);
            return ResponseEntity.ok(savedAddress);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    // Get address of the CURRENT logged-in user
    @GetMapping("/my-address")
    public ResponseEntity<?> getAddress() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Address address = addressService.getAddressByEmail(email);
        
        if (address == null) {
            return ResponseEntity.status(404).body("No address found for your account");
        }
        return ResponseEntity.ok(address);
    }
}