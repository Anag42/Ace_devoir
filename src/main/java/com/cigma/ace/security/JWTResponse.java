package com.cigma.ace.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTResponse {
	private Object user;
	private String token;
}
