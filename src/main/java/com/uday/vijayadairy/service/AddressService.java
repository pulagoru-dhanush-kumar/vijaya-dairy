package com.uday.vijayadairy.service;

import com.uday.vijayadairy.model.Address;
import com.uday.vijayadairy.model.User;
import com.uday.vijayadairy.repository.AddressRepository;
import com.uday.vijayadairy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public Address saveAddressByEmail(String email, Address address) {
        // 1. Find user by email (from your User class's @Column(unique = true))
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // 2. Check if user already has an address to avoid duplicates
        Address existingAddress = addressRepository.findByUser(user);
        if (existingAddress != null) {
            // Update existing fields instead of creating a new row
            existingAddress.setLocation(address.getLocation());
            existingAddress.setPIN(address.getPIN());
            existingAddress.setCity(address.getCity());
            existingAddress.setStreet(address.getStreet());
            return addressRepository.save(existingAddress);
        }

        // 3. Link user to new address and save
        address.setUser(user);
        return addressRepository.save(address);
    }

    public Address getAddressByEmail(String email) {
        return addressRepository.findByUserEmail(email);
    }
}