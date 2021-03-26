package com.cigma.ace.dto;

import com.cigma.ace.annotation.Unique;
import com.cigma.ace.enums.Role;
import com.cigma.ace.service.implementations.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private Long Id;
	@Unique(service = UserService.class, fieldName = "email", message = "{email.unique.violation}")
	private String email;
	@Unique(service = UserService.class, fieldName = "username", message = "{username.unique.violation}")
	private String username;
	private String telephone;
	private Role role;
}
