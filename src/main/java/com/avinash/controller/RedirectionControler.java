package com.avinash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectionControler {

	@GetMapping("/redirectToLinkedIn")
	public String redirectToLinkedIn() {
		System.out.println("Inside RedirectionController");
		return "redirect:https://www.linkedin.com";
	}
}
