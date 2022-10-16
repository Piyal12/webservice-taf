package com.im.service.exception;

@SuppressWarnings("serial")
public class PostgresqlException extends RuntimeException {

	private String message;

	public PostgresqlException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "PostgresqlException [message=" + message + "]";
	}

}
