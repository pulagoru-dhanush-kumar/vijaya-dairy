package com.uday.vijayadairy.model;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.TrueFalseConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
@Entity(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	@ManyToOne()
	@JoinColumn(nullable = false, name="user_id")
	User user;
	@ManyToMany
    @JoinTable(name="order_products",joinColumns = @JoinColumn(name="order_id"),
    inverseJoinColumns = @JoinColumn(name="product_id"))
	List<Product> products;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	OrderStatus status=OrderStatus.ACCEPTED;
	Date orderdate;
	int quantity;
	@CreationTimestamp
	private LocalDateTime created_at;
	@UpdateTimestamp
	private LocalDateTime  updated_at;
    
}
