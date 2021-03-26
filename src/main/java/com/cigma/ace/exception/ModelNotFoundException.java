package com.cigma.ace.exception;

@SuppressWarnings("serial")
public class ModelNotFoundException extends RuntimeException  {
	public ModelNotFoundException(Class<?> model) {
		super("Model " + model.getSimpleName() + " not found!");
	}

	public ModelNotFoundException(Class<?> model, Long id) {
		super("Model " + model.getSimpleName() + " of id " + id + " not found!");
	}
}
