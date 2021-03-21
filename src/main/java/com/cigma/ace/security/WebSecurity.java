package com.cigma.ace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.cigma.ace.config.SecurityConstants;
import com.cigma.ace.exception.RestAccessDeniedHandler;
import com.cigma.ace.exception.RestAuthenticationEntryPoint;
import com.cigma.ace.service.implementations.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	RestAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
		.antMatchers(HttpMethod.GET, "/api/v1/users").permitAll()
		.antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
		.antMatchers(HttpMethod.GET, "/api/v1/users/{id}").permitAll()
		.antMatchers(HttpMethod.PUT, "/api/v1/users/{id}").hasRole("USER")
		.antMatchers(HttpMethod.DELETE, "/api/v1/users/{id}").hasRole("ADMIN")
		.anyRequest().authenticated()
//		.and().exceptionHandling()
//		.authenticationEntryPoint(authenticationEntryPoint)
//		.accessDeniedHandler(accessDeniedHandler)
		.and()
		.addFilter(new JWTAuthenticationFilter(authenticationManager()))
        .addFilter(new JWTAuthorizationFilter(authenticationManager()))
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
	
}
