package com.cigma.ace.controller;

import com.cigma.ace.dto.ForgotPasswordDTO;
import com.cigma.ace.exception.ModelNotFoundException;
import com.cigma.ace.model.PasswordReset;
import com.cigma.ace.model.User;
import com.cigma.ace.service.implementations.PasswordResetService;
import com.cigma.ace.service.implementations.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.security.util.Password;

import javax.validation.Valid;
import java.util.Optional;

@RequestMapping("/api/v1/password")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordResetService passwordResetService;

    @PostMapping
    public void forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgetPasswordDTO) {
        User user = userService.findByEmail(forgetPasswordDTO.getEmail());

        if (user == null)
            throw new ModelNotFoundException(User.class);

        PasswordReset passwordReset = passwordResetService.save();

    }

    @PutMapping("/reset")
    public void resetPassword() {

    }
}
