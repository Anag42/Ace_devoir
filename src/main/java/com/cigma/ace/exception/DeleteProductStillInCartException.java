package com.cigma.ace.exception;

@SuppressWarnings("serial")
public class DeleteProductStillInCartException extends RuntimeException {
	public DeleteProductStillInCartException() {
		super("Can't Delete Product if its still in Users Carts");
	}
}
