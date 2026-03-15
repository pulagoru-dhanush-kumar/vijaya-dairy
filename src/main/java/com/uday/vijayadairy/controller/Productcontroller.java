package com.uday.vijayadairy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.stream.Collectors;

import javax.naming.spi.DirStateFactory.Result;

import com.uday.vijayadairy.model.Categories;
import com.uday.vijayadairy.model.Product;
import com.uday.vijayadairy.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class Productcontroller {
	@Autowired
	ProductService productService;

	@PostMapping(value = "/save")
	public ResponseEntity<?> productinput(@Valid @RequestBody  Product product) {

		try {
			
			Product saved = productService.saveproduct(product);
			return ResponseEntity.ok(saved);
		} catch (Exception e) {
			e.printStackTrace(); // VERY IMPORTANT FOR DEBUG
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
		}
	}
	@GetMapping("/all")
	public ResponseEntity<Map<String, Object>> getAllProductsWithImages() {
		List<Product> products = productService.getAllProducts();
		LinkedHashMap<String, Object> result=new LinkedHashMap<String, Object>();
		List<Map<String,Object>> data=new ArrayList<>();
		for(Product product:products)
		{
			LinkedHashMap<String, Object> map=new LinkedHashMap<String, Object>();
			map.put("pid",product.getPid());
			map.put("name", product.getName());
			map.put("price", product.getPrice());
			map.put("imageurl", product.getImageurl());
			map.put("category", product.getCategory());
			map.put("quantity", product.getQuantity());
			data.add(map);
		}
		result.put("data", data);
		result.put("total_size", data.size());
		result.put("productStatus", HttpStatus.OK.value());
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	@GetMapping("/category/all")
	public ResponseEntity<Map<String, Object>> getProductsByCategory(@Valid @RequestHeader("Category") Categories categories)
	{
		Map<String,Object> result=new LinkedHashMap();
		System.out.println(categories);
		List<Product> data=productService.getAllProductsByCategory(categories.valueOf(categories.value.toUpperCase()));
		result.put("data",data);
		result.put("category_total", data.size());
		result.put("productStatus", HttpStatus.OK.value());
		return new ResponseEntity<Map<String,Object>>(result,HttpStatus.OK);
	}
		
	
	
}
