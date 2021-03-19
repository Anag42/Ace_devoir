package com.cigma.ace.dto;

import com.cigma.ace.annotation.Unique;
import com.cigma.ace.service.IUserService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	@Unique(service = IUserService.class, fieldName = "email", message = "{email.unique.violation}")
	private String email;
	@Unique(service = IUserService.class, fieldName = "username", message = "{username.unique.violation}")
	private String username;
	private String telephone;
}
