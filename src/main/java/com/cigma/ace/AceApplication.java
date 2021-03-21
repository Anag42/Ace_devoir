package com.cigma.ace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cigma.ace.config.AdminCredentials;
import com.cigma.ace.enums.Role;
import com.cigma.ace.model.User;
import com.cigma.ace.repository.UserRepository;

@SpringBootApplication
public class AceApplication {

	@Autowired
	AdminCredentials adminCredentialsConfig;
	
	@Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(AceApplication.class, args);
	}

	@Bean
    public CommandLineRunner run(UserRepository userRepository) throws Exception {
        return (String[] args) -> {      	
        	List<User> adminsList = userRepository.findByRole(Role.ADMIN);
        	User admin;
        	if(adminsList.isEmpty()) {
        		admin = new User();
                admin.setUsername(adminCredentialsConfig.getUsername());
                admin.setEmail(adminCredentialsConfig.getEmail());  
                admin.setPassword(bCryptPasswordEncoder.encode(adminCredentialsConfig.getPassword()));             
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
        	} else {
        		admin = adminsList.get(0);
        	}
        	
        	System.out.println("ADMIN CREDENTIALS : " + adminCredentialsConfig.getUsername() + " " + adminCredentialsConfig.getPassword());        
        };
    }
	
}
