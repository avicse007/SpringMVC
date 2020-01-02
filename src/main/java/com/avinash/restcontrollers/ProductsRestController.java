package com.avinash.restcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.avinash.model.Product;
import com.avinash.repository.ProductRepository;

@RestController
public class ProductsRestController {

	@Autowired
	private ProductRepository productRepository; 
	
	/*
	@GetMapping("/hplus/rest/products")
	//No need of this annotation if the class is annotated with @RestController
	//This @ResponseBody tells that the list return by the method should be 
	// the part of response body
	//@ResponseBody
	public List<Product> getProducts(){
		List<Product> products = new ArrayList<Product>();
		Iterable<Product> productsIter= productRepository.findAll();
		productsIter.forEach(product->products.add(product));
		return products;
	}
	*/
	
	@GetMapping("/hplus/rest/products")
	public ResponseEntity getProductsByRequestParam(@RequestParam("name")String name){
		List<Product> products = productRepository.searchByName(name);
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	
	@GetMapping("/hplus/rest/products/{name}")
	public ResponseEntity getProductsByPathVariable(@PathVariable("name")String name){
		List<Product> products = productRepository.searchByName(name);
		return new ResponseEntity<>(products,HttpStatus.OK);
	}
	
}
