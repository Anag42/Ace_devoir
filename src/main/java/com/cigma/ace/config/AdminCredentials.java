package com.cigma.ace.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "admin")
@Data
public class AdminCredentials {
	private String email;
	private String username;
	private String password;
}
