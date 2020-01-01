package com.avinash.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.avinash.model.User;
import com.avinash.repository.UserRepository;

import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy.ResubmissionScheduler;

@Controller
public class RegistrationController {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, "dateOfBirth",new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
			
	}
	
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/registeruser")
	public String registerUser( @Valid @ModelAttribute("newUser")User user,BindingResult result, Model model) {
		System.out.println("In registration Controller");
		if(result.hasErrors()) {
			for(ObjectError e : result.getAllErrors()) {
				System.out.println("Errors : "+ e.getDefaultMessage());
			}
			return "register";
			}
		userRepository.save(user);
		model.addAttribute("dataSaved","User was registered Successfully");
		return "login";
	}
}
