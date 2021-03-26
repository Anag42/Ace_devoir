package com.cigma.ace.controller;

import com.cigma.ace.dto.ForgetPasswordDTO;
import com.cigma.ace.dto.ForgetPasswordMapper;
import com.cigma.ace.dto.PasswordResetDTO;
import com.cigma.ace.exception.InvalidPasswordResetTokenException;
import com.cigma.ace.exception.ModelNotFoundException;
import com.cigma.ace.model.PasswordReset;
import com.cigma.ace.model.User;
import com.cigma.ace.repository.PasswordResetRepository;
import com.cigma.ace.service.implementations.PasswordResetService;
import com.cigma.ace.service.implementations.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/password")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordResetService passwordResetService;
    
    @Autowired
    PasswordResetRepository passwordResetRepository;
    
    @Autowired
    ForgetPasswordMapper forgetPasswordMapper;

    @PostMapping
    public ResponseEntity<HttpStatus> forgotPassword(@Valid @RequestBody ForgetPasswordDTO forgetPasswordDTO) throws MessagingException, IOException {
        User user = userService.findByEmail(forgetPasswordDTO.getEmail());

        if (user == null)
            throw new ModelNotFoundException(User.class);

        passwordResetService.save(forgetPasswordMapper.toPasswordReset(forgetPasswordDTO));
        
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/reset")
    public ResponseEntity<HttpStatus> resetPassword(@Valid @RequestBody PasswordResetDTO passwordResetDTO) throws InvalidPasswordResetTokenException, ModelNotFoundException {
    	
    	PasswordReset passwordReset = passwordResetRepository.findByEmail(passwordResetDTO.getEmail());
    	
    	if (passwordReset == null)
            throw new ModelNotFoundException(PasswordReset.class);
    	
    	if (!passwordReset.getToken().equals(passwordResetDTO.getToken()))
    		throw new InvalidPasswordResetTokenException();
    	
    	passwordResetService.changePassword(passwordResetDTO);
    	
    	return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
