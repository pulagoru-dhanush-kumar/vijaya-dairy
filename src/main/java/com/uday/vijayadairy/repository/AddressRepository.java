package com.uday.vijayadairy.repository;



import com.uday.vijayadairy.model.Address;
import com.uday.vijayadairy.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    

	Address findByUserEmail(String email);
    Address findByUser(User user);
}
