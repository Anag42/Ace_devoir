package com.cigma.ace.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cigma.ace.exception.RestAccessDeniedHandler;
import com.cigma.ace.exception.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class ProjectConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	RestAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.authorizeRequests().anyRequest().permitAll();
//				.antMatchers("/api/user/signup").permitAll()
//				.antMatchers("/api/user/signin").permitAll()
//				.antMatchers("/api/user/forgot/password").permitAll()
//				.antMatchers("/api/user/reset/password").permitAll()
//				.antMatchers("/").permitAll()
//				.antMatchers("/admin").hasAnyRole()
//				.antMatchers("/user").hasAnyRole()
//				.anyRequest()
//					.authenticated()
//				.and()
//				.formLogin();
//				.authenticated()
//			.and()
//			.exceptionHandling()
//				.authenticationEntryPoint(authenticationEntryPoint)
//				.accessDeniedHandler(accessDeniedHandler)
//			.and().formLogin();
//			.and()
//			.exceptionHandling()
//				.authenticationEntryPoint(authenticationEntryPoint)
//				.accessDeniedHandler(accessDeniedHandler)
//			.and()
//			.sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
}
