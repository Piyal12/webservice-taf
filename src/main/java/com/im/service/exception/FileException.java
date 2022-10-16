package com.im.service.exception;

@SuppressWarnings("serial")
public class FileException extends RuntimeException {
	public String message;
	
	public FileException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "FileException [message=" + message + "]";
	}
}
