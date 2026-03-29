package com.uday.vijayadairy.model;

import java.awt.Image;
import java.time.LocalDateTime;
import java.util.List;

import org.aspectj.apache.bcel.util.ByteSequence;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints =  {@UniqueConstraint(columnNames = {"name","price","quantity"})})
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long pid;
	
	@NotNull(message="name should not be empty")
	String name;
	
	@NotNull(message = "price should not be empty")
	@Positive
	Float price;
	
	@ManyToMany(mappedBy = "products")
	@JsonIgnore
    List<Order> orders;
	@NotNull(message ="image url should not be empty")
	
	String imageurl;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	Categories category;
	
	String quantity;
	
	@Transient
	float quantityvalue;
	
	@Transient
	String unit;
	
	@PrePersist
	@PreUpdate
	public void buildQuantity()
	{
		this.quantity=quantityvalue+unit;
		System.out.println("the total quantity is "+quantity);
	}
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	ProductStatus productStatus=ProductStatus.DRAFT;
	@CreationTimestamp
	private LocalDateTime created_at;
	@UpdateTimestamp
	private LocalDateTime  updated_at;
	@NotNull
	private int available_items;

}
