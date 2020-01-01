package com.avinash.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.avinash.model.Login;
import com.avinash.model.User;

@ControllerAdvice
public class DefaultModelAttributeController {
	
	@ModelAttribute("newUser")
    public User getDefualtUser() {
    	return new User();
    }
    
    @ModelAttribute("genderItems")
    public List<String> getGenderItems() {
    	return Arrays.asList(new String[] {"Male","Female","Other"});
    }
    
    @ModelAttribute("login")
    public Login getDefualtLogin() {
    	return new Login();
    }

}
