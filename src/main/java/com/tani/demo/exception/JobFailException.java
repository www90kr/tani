package com.tani.demo.exception;

import lombok.*;

@AllArgsConstructor
public class JobFailException extends RuntimeException {
	String message;
	@Override
	public String getMessage() {
		return message;
	}
}
