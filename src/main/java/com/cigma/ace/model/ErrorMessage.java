package com.cigma.ace.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor

public class ErrorMessage {
	@NonNull private String message;
	private String timestamp;

	public ErrorMessage(String message){
		this.message = message;
		this.timestamp = LocalDateTime.now().toString();
	}
}
