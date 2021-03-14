package com.cigma.ace.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor    
@AllArgsConstructor
public class BadRequestError {
	private String fieldName;
    private Object rejectedValue;
    private String messageError;
}
