package com.im.service.exception;

@SuppressWarnings("serial")
public class ConfigFileException extends RuntimeException {
	public String message;
	
	public ConfigFileException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ConfigFileException [message=" + message + "]";
	}

}
