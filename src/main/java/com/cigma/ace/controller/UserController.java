package com.cigma.ace.controller;

import org.springframework.security.access.AccessDeniedException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/api/user/signup")
	public String signup(){
		return "User Signed Up!";
	}
	
	@GetMapping("/api/user/signin")
	public void  signin() throws AccessDeniedException{
		throw new AccessDeniedException("Access Denied!");
	}
	
	@GetMapping("/api/user/info")
	public String info(){
		
		return "User Information!";
	}
}
