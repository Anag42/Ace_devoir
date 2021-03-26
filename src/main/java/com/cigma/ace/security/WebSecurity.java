package com.cigma.ace.security;

import com.cigma.ace.dto.UserMapper;
import com.cigma.ace.security.implementations.UserDetailsImpl;
import com.cigma.ace.security.implementations.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.cigma.ace.config.TokenProvider;
import com.cigma.ace.exception.RestAccessDeniedHandler;
import com.cigma.ace.exception.RestAuthenticationEntryPoint;
import com.cigma.ace.repository.UserRepository;
import com.cigma.ace.service.implementations.UserService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	RestAccessDeniedHandler accessDeniedHandler;

	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, TokenProvider.SIGN_UP_URL).permitAll()
		.antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
//		.antMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)
		.and().addFilter(new JWTAuthenticationFilter(authenticationManager(), userMapper)).addFilter(new JWTAuthorizationFilter(authenticationManager(), userRepository))
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
