package com.avinash.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

	@GetMapping("/logout")
	public String logOut(HttpSession session) {
		System.out.println("Inside logoutController");
		System.out.println("Ending usersession");
		session.invalidate();
		//System.out.println("Ensuring session has ended "+session.getAttribute("login"));
		return "login";
	}
}
