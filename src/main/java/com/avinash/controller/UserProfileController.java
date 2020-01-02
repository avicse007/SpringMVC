package com.avinash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.avinash.model.Login;

@Controller
public class UserProfileController {

	@PostMapping("/userprofile")
	public String getUserProfile( @SessionAttribute("login") Login login,Model model) {
		System.out.println("Inside UserProfile Controller");
		System.out.println("User name from session Object "+login.getUsername());
		model.addAttribute("username",login.getUsername());
		return "profile";
	}
	
}
