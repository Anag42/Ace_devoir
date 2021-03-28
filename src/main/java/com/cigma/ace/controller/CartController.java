package com.cigma.ace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cigma.ace.dto.mappers.UserMapper;
import com.cigma.ace.model.User;
import com.cigma.ace.service.implementations.UserService;


@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	UserService userService;
	
	@PreAuthorize("hasRole('CLIENT')")
	@GetMapping("/add")
	public Object add(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return user;
	}

}
