package com.cigma.ace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cigma.ace.config.AdminCredentialsConfig;
import com.cigma.ace.enums.Role;
import com.cigma.ace.model.User;
import com.cigma.ace.service.UserServiceImpl;

@SpringBootApplication
public class AceApplication {
	
	@Autowired
	AdminCredentialsConfig adminCredentialsConfig;
	
	public static void main(String[] args) {
		SpringApplication.run(AceApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner run(UserServiceImpl userService) throws Exception {
        return (String[] args) -> {
        	List<User> adminsList = userService.findByRole(Role.ADMIN);
        	User admin;
        	if(adminsList.isEmpty()) {
        		admin = new User();
                admin.setUsername(adminCredentialsConfig.getUsername());
                admin.setEmail(adminCredentialsConfig.getEmail());            
                admin.setRole(Role.ADMIN);
                userService.save(admin);
                
        	} else {
        		admin = adminsList.get(0);
        	}
        	
        	System.out.println("ADMIN CREDENTIALS : " + admin.getUsername() + " " + admin.getPassword());        
        };
    }
	
}
