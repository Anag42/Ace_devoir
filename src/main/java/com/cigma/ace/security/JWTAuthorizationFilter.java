package com.cigma.ace.security;

import java.io.IOException;
import java.util.Collection;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.cigma.ace.model.User;
import com.cigma.ace.repository.UserRepository;
import com.cigma.ace.security.implementations.UserDetailsImpl;
import com.cigma.ace.service.implementations.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cigma.ace.config.TokenProvider;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	@Autowired
    UserRepository userRepository;

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(TokenProvider.HEADER_STRING);

        if (header == null || !header.startsWith(TokenProvider.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(TokenProvider.HEADER_STRING);

        if (token != null) {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(TokenProvider.SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TokenProvider.TOKEN_PREFIX, ""));

            String username = decodedJWT.getSubject();

            User user = userRepository.findByUsername(username);

            if (user != null) {
                UserDetailsImpl userDetails = new UserDetailsImpl(user);
                return new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
            }

            return null;
        }

        return null;
    }
}
