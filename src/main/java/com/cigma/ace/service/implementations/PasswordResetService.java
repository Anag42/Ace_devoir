package com.cigma.ace.service.implementations;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cigma.ace.dto.PasswordResetDTO;
import com.cigma.ace.exception.ModelNotFoundException;
import com.cigma.ace.mail.PasswordResetMail;
import com.cigma.ace.model.PasswordReset;
import com.cigma.ace.model.User;
import com.cigma.ace.repository.PasswordResetRepository;
import com.cigma.ace.repository.UserRepository;
import com.cigma.ace.util.RandomStringGenerator;

@Service
public class PasswordResetService {

	@Autowired
    PasswordResetRepository passwordResetRepository;
	
	@Autowired
    UserRepository userRepository;
	
	@Autowired
    EmailService emailService;
	
	@Autowired
    PasswordResetMail passwordResetMail;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(PasswordReset forgetPassword) throws MessagingException, IOException {
    	PasswordReset passwordReset = passwordResetRepository.findByEmail(forgetPassword.getEmail());
    	
    	if(passwordReset != null)
    		passwordResetRepository.delete(passwordReset);
    	
    	forgetPassword.setToken(RandomStringGenerator.alphaNumericString(30));
        passwordResetRepository.save(forgetPassword);
        
        String subject = "Password Reset!";
    	String body = passwordResetMail.build(forgetPassword);
        emailService.sendHtmlMail(forgetPassword.getEmail(), subject, body);
    }
    
    public void changePassword(PasswordResetDTO passwordResetDTO) throws ModelNotFoundException {
    	User user = userRepository.findByEmail(passwordResetDTO.getEmail());
    	
    	if (user == null)
            throw new ModelNotFoundException(User.class);
    	
    	user.setPassword(bCryptPasswordEncoder.encode(passwordResetDTO.getPassword()));
    	userRepository.save(user);
    }
}
