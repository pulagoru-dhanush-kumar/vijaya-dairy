package com.uday.vijayadairy.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name","email"})})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true ,nullable = false)
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email should not have spaces")
	private String email;
	@Column(name="encrypted_password",nullable = false)
	@NotBlank(message="passwords should not be empty")
	private String password;
	@Transient
	String confirmpassword;
	@CreationTimestamp
	private LocalDateTime created_at;
	@UpdateTimestamp
	private LocalDateTime  updated_at;
	@OneToMany(mappedBy = "user")
	private List<Order> orders;	
	@NotBlank(message = "please enter your mobile number")
	@Length(min=10,max=10,
			message="[Should have any spaces ,Should have only digits,should not exceeed 10 digits]")
	private	String mobilenumber;
	private String name;
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Cart cart;
	
}



