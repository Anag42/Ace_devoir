package com.cigma.ace.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenProvider {
  public static final String SECRET = "Zq4t7w!z%C*F-JaNdRgUjXn2r5u8x/A?";
  public static final long EXPIRATION_TIME = 900_000; // 15 mins
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String SIGN_UP_URL = "/api/v1/users/login";
  public static final String AUTHORITIES_KEY = "roles";
}