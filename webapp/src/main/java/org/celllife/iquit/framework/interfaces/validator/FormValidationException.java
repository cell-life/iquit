package org.celllife.iquit.framework.interfaces.validator;

/**
 * Exception that indicates that a validation error has been detected in a specific form.
 * 
 * For example, that a number between 1 and 45 was expected, but the value was 46.
 */
public class FormValidationException extends RuntimeException {

	private static final long serialVersionUID = 1103093092080632990L;

	public FormValidationException() {
		super();
	}

	public FormValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public FormValidationException(String message) {
		super(message);
	}

	public FormValidationException(Throwable cause) {
		super(cause);
	}
}
