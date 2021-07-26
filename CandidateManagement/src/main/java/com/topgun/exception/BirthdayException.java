package com.topgun.exception;

public class BirthdayException extends Exception {
	private static final long serialVersionUID = 1L;

	private String message;

	public BirthdayException(String message) {
		super();
		this.message = message;
	}

	public void getMessageException() {
		System.out.println(message);
	}
}
