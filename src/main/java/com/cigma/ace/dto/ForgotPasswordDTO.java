package com.cigma.ace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordDTO {
    @Email(message = "*Please provide a valid email")
    private String email;
}
