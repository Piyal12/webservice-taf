package com.im.service.exception;

@SuppressWarnings("serial")
public class NullException extends RuntimeException {

	private String message;

	public NullException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "NullException [message=" + message + "]";
	}
	
	
	
}
