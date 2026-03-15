package com.uday.vijayadairy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.uday.vijayadairy.model.User;
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Integer>{
	@Query("select u from User u where u.email = :email")
	public Optional<User> findByEmail(@Param("email")String email);
	
	@Query("select u from User u where u.email = :email")
	public User findByName(@Param("email")String email);

	
	
}
