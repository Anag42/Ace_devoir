package com.cigma.ace.dto;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ForgetPasswordDTO {
	@Email(message = "*Please provide a valid email")
    private String email;
}
