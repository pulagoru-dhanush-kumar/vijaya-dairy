package com.uday.vijayadairy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uday.vijayadairy.model.Categories;
import com.uday.vijayadairy.model.Product;
import com.uday.vijayadairy.model.ProductStatus;

public interface ProductRepository extends CrudRepository<Product, Long> {
	
	@Query("select p from Product p where p.category= :category and p.productStatus= 'ACTIVE'")
	public List<Product> getAllProductsByCategory(Categories category) ;//this is for live users
	
	@Query("select p from Product p where p.name= ?1 and p.price= ?2 and p.quantity =?3")
	public Product checkProduct(String name,Float price,String quantity);
	
	List<Product> findByProductStatus(ProductStatus productStatus);
	List<Product> findByCategory(String category);
	List<Product> findByNameContainingIgnoreCase(String name);
	
	@Query("select p from Product p where p.productStatus= 'ACTIVE'")
	List<Product> findActiveProducts();
	
	
	
	
	
	
}
 