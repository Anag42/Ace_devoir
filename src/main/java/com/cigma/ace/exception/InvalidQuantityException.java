package com.cigma.ace.exception;

@SuppressWarnings("serial")
public class InvalidQuantityException extends RuntimeException  {
	public InvalidQuantityException(Integer integer) {
		super("Available Stock is just : " + integer);
	}
}
