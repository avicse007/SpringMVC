package com.avinash.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.avinash.model.User;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String goHome(){
        System.out.println("in home controller");
        return "index";
    }
    
    @GetMapping("/goToSearch")
    public String goToSearch() {
    	System.out.println("in home controller going to search page");
    	return "search";
    }
    
    @GetMapping("/goToLogin")
    public String goToLogin() {
    	System.out.println("in home controller going to goToLogin page");
    	return "login";
    }
    
    @GetMapping("/goToRegistration")
    public String goToRegistration() {
    	System.out.println("in home controller going to goToRegistration page");
    	return "register";
    }
    
    @ModelAttribute("newUser")
    public User getDefualtUser() {
    	return new User();
    }
    
    @ModelAttribute("genderItems")
    public List<String> getGenderItems() {
    	return Arrays.asList(new String[] {"Male","Female","Other"});
    }
}
