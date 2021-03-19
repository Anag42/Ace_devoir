package com.cigma.ace.service.implementations;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.cigma.ace.mail.WelcomeMail;
import com.cigma.ace.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.cigma.ace.enums.Role;
import com.cigma.ace.model.User;
import com.cigma.ace.repository.UserRepository;
import com.cigma.ace.util.RandomStringGenerator;

import javax.mail.MessagingException;

@Service
public class UserService implements IUserService {

	@Autowired 
	UserRepository userRepository;

	@Autowired
    PasswordEncoder passwordEncoder;

	@Autowired
    WelcomeMail welcomeMail;

	@Autowired
    EmailService emailService;
	
	public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    public List<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }
    
    public Boolean existsByEmail(String string) {
        return userRepository.existsByEmail(string);
    }
    
    public Boolean existsByUsername(String string) {
        return userRepository.existsByUsername(string);
    }

    public void create(User user) throws IOException, MessagingException {
    	String password = RandomStringGenerator.alphaNumericString(15);
    	String subject = "Welcome " + user.getUsername().toUpperCase() + "!";
    	String body = welcomeMail.build(user, password);
        emailService.sendSimpleEmail(user.getEmail(), subject, body);
    	user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public void update(User user){
        userRepository.save(user);
    }

    public void deleteById(Long id) {
    	userRepository.deleteById(id);	
    }

	@SuppressWarnings("deprecation")
	@Override
	public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
		Assert.notNull(fieldName);

        if (!fieldName.equals("email") && !fieldName.equals("username")) {
            throw new UnsupportedOperationException("Field name not supported");
        }

        if (value == null) {
            return false;
        }

        if(fieldName.equals("email")) {
        	return this.userRepository.existsByEmail(value.toString());
        } else {
        	return this.userRepository.existsByUsername(value.toString());
        }
	}
}
