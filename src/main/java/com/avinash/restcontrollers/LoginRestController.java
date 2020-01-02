package com.avinash.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.avinash.exceptions.LoginFailureException;
import com.avinash.model.Login;
import com.avinash.model.User;
import com.avinash.repository.UserRepository;

@RestController
public class LoginRestController {

	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/hplus/rest/login")
	public ResponseEntity loginUser(@RequestBody Login login) throws LoginFailureException {
		
		System.out.println("Inside LoginRestController with username "
				+login.getUsername());
		User user = userRepository.searchByName(login.getUsername());
		if(user==null) {
			return new ResponseEntity<>("User with username "
		             +login.getUsername()+
					" does not exits",HttpStatus.NOT_FOUND);
		}
		
		else if(user.getPassword().equals(login.getPassword()) && 
				user.getUsername().equals(login.getUsername())){
			
			return new ResponseEntity<>("Welcome "+user.getFirstName(),HttpStatus.OK);
		}
		else
			throw new LoginFailureException("Invalid username or Password");
		
	}
}
