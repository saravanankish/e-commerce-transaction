package com.saravanank.ecommerce.transaction.exceptions;

import org.springframework.http.HttpStatus;

public class AuthHeaderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private HttpStatus errorCode = HttpStatus.UNAUTHORIZED;
	private String errorMessage;

	public AuthHeaderNotFoundException(HttpStatus errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public AuthHeaderNotFoundException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public HttpStatus getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(HttpStatus errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
