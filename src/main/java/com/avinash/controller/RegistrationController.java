package com.avinash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.avinash.model.User;
import com.avinash.repository.UserRepository;

@Controller
public class RegistrationController {
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/registeruser")
	public String registerUser(@ModelAttribute("newUser")User user,Model model) {
		System.out.println("In registration Controller");
		userRepository.save(user);
		model.addAttribute("dataSaved","User was registered Successfully");
		return "login";
	}
}
