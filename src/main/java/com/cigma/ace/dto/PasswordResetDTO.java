package com.cigma.ace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetDTO {
    @Email(message = "*Please provide a valid email")
    private String email;
    @NotBlank(message = "*Please provide a token")
    private String token;
    @NotBlank(message = "*Please provide a new password")
    private String password;
}
