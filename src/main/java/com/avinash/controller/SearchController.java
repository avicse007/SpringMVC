package com.avinash.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;

import com.avinash.exceptions.ApplicationException;
import com.avinash.model.Product;
import com.avinash.repository.ProductRepository;

@Controller
public class SearchController {
	
	@Autowired
	private ProductRepository productRepository;

	
	/* Callable implementation of the async task executor.
	@GetMapping("/search")
	public Callable<String> search(@RequestParam("search")String search,Model model, HttpServletRequest request ) {
		System.out.println("Inside search controller");
		System.out.println("Search Criteria "+search);
		System.out.println("Is Async supported "+request.isAsyncSupported());
		System.out.println("Thread from controller "+Thread.currentThread().getName());
		return ()->{
			//To simulate the Delay
			Thread.sleep(3000);
		System.out.println("Async Thread "+Thread.currentThread().getName());	
		List<Product> products = new ArrayList<Product>();
		products = productRepository.searchByName(search);
		model.addAttribute("products",products);
		return "search";
		};
		*/
	
	//DeferredResult Implementation of the controller
	
	@Autowired
	private AsyncTaskExecutor taskExecutor;
	
	DeferredResult<String> deferredResult = new DeferredResult<>();
	
	@GetMapping("/search")
	public DeferredResult<String> search(@RequestParam("search")String search,Model model, HttpServletRequest request ) {
		System.out.println("Inside search controller");
		System.out.println("Search Criteria "+search);
		System.out.println("Is Async supported "+request.isAsyncSupported());
		System.out.println("Thread from controller "+Thread.currentThread().getName());
		taskExecutor.execute(()->{
			try {
			Thread.sleep(3000);
			//To invoke AsyncRequestTimeoutError and return error.jsp
			//Thread.sleep(7000);
			}catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("Async Thread "+Thread.currentThread().getName());	
			List<Product> products = new ArrayList<Product>();
			products = productRepository.searchByName(search);
			if(products==null)
				throw new ApplicationException("No product found with search key "+search);
			model.addAttribute("products",products);
			deferredResult.setResult("search");
		});
		return deferredResult;
	}
	
	
}
