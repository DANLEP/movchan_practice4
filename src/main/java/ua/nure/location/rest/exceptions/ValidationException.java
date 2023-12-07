package ua.nure.location.rest.exceptions;

public class ValidationException extends RuntimeException {

	public ValidationException() {
		super();
	}

	public ValidationException(String message) {
		super(message);
	}

}
