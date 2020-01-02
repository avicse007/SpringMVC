package com.avinash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.avinash.exceptions.ApplicationException;
import com.avinash.model.Login;
import com.avinash.model.User;
import com.avinash.repository.UserRepository;

@Controller
@SessionAttributes("login")
public class LoginController {
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/login")
	public String login(@ModelAttribute("login")Login login) {
		User user = userRepository.searchByName(login.getUsername());
		if(user!=null && user.getPassword().equals(login.getPassword())){
			return "forward:/userprofile";
		}
		else {
			throw new ApplicationException("Either user name or Password is incorrect");
		}
	}
	
	@ExceptionHandler(ApplicationException.class)
	public String handleException() {
		System.out.println("Inside handleException in LoginController" );
		return "error";
	}
	
}
