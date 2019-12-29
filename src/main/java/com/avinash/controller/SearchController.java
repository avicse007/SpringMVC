package com.avinash.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.avinash.model.Product;
import com.avinash.repository.ProductRepository;

@Controller
public class SearchController {
	
	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/search")
	public String search(@RequestParam("search")String search,Model model ) {
		System.out.println("Inside search controller");
		List<Product> products = new ArrayList<Product>();
		products = productRepository.searchByName(search);
		model.addAttribute("products",products);
		return "search";
	}
}
