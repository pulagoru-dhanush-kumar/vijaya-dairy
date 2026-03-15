package com.uday.vijayadairy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uday.vijayadairy.model.Categories;
import com.uday.vijayadairy.model.Product;
import com.uday.vijayadairy.repository.ProductRepository;
@Service
public class ProductService {

@Autowired
 ProductRepository productrepository;

public Product saveproduct(Product product)
{
	Product product1=productrepository.checkProduct(product.getName(), product.getPrice(), product.getQuantity());
	if(product1!=null) throw new RuntimeException("this product already exists please eneter another product");	
	return productrepository.save(product);
}
public List<Product> getAllProducts() {
    return (List<Product>) productrepository.findActiveProducts();
}
public List<Product> getAllProductsByCategory(Categories categories) {
	System.out.println( productrepository.getAllProductsByCategory(categories));
	return productrepository.getAllProductsByCategory(categories);
	}


}
