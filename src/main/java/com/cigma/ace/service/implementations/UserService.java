package com.cigma.ace.service.implementations;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.cigma.ace.enums.Role;
import com.cigma.ace.mail.WelcomeMail;
import com.cigma.ace.model.User;
import com.cigma.ace.repository.UserRepository;
import com.cigma.ace.service.IFieldValueExists;
import com.cigma.ace.util.RandomStringGenerator;

@Service
public class UserService implements IFieldValueExists, UserDetailsService {

	@Autowired 
	UserRepository userRepository;

	@Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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
    
    public User findByUsername(String string) {
        return userRepository.findByUsername(string);
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

    public User create(User user) throws IOException, MessagingException {
    	String password = RandomStringGenerator.alphaNumericString(15);
    	String subject = "Welcome " + user.getUsername().toUpperCase() + "!";
    	String body = welcomeMail.build(user, password);
        emailService.sendHtmlMail(user.getEmail(), subject, body);
    	user.setPassword(bCryptPasswordEncoder.encode(password));
    	user.setRole(Role.CLIENT);
        userRepository.save(user);
        
        return user;
    }

    public void update(User user){
        userRepository.save(user);
    }

    public void deleteById(Long id) {
    	userRepository.deleteById(id);	
    }
    
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
        
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return user;
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
