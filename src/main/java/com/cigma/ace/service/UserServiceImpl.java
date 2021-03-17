package com.cigma.ace.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.cigma.ace.dto.UserMapper;
import com.cigma.ace.enums.Role;
import com.cigma.ace.model.User;
import com.cigma.ace.repository.UserRepository;
import com.cigma.ace.util.RandomStringGenerator;

@Service
public class UserServiceImpl implements UserService {

	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	UserMapper userMapper;
	
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

    public User save(User user) {
    	String password = RandomStringGenerator.alphaNumericString(12);
    	user.setPassword(password);
        return userRepository.save(user);
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
        
//        Boolean isValid = false;
        
        if(fieldName.equals("email")) {
        	return this.userRepository.existsByEmail(value.toString());
        } else if(fieldName.equals("username")) {
        	return this.userRepository.existsByUsername(value.toString());
        }

        return true;
	}
}
