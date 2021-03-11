package com.cigma.ace.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import com.cigma.ace.exception.RestAccessDeniedHandler;
import com.cigma.ace.exception.RestAuthenticationEntryPoint;

@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RestAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private RestAuthenticationEntryPoint authenticationEntryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
				.antMatchers("/api/user/signup").permitAll()
				.antMatchers("/api/user/signin").permitAll()
				.antMatchers("/api/user/forgot/password").permitAll()
				.antMatchers("/api/user/reset/password").permitAll()
			.anyRequest()
				.authenticated()
			.and()
			.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler)
			.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
