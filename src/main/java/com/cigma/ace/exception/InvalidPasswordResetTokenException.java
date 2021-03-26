package com.cigma.ace.exception;

@SuppressWarnings("serial")
public class InvalidPasswordResetTokenException extends RuntimeException  {
	public InvalidPasswordResetTokenException() {
		super("Password Reset Token Invalid");
	}
}
