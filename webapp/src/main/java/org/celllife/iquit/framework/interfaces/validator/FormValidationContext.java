package org.celllife.iquit.framework.interfaces.validator;

import java.io.Serializable;
import java.util.Map;

/**
 * Contains the form to be validated (in Map format) and the result of the
 * validation.
 */
public class FormValidationContext implements Serializable {

	private static final long serialVersionUID = -1352267346055303306L;

	private Map<String, String> parameters;
	private Boolean success = true;
	private String validationError;

	public FormValidationContext() {

	}

	public FormValidationContext(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Sets the parameters to use while processing rules
	 * 
	 * @param parameters
	 *            Map with parameter name/value
	 */
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public Object get(String key) {
		return parameters.get(key);
	}

	/**
	 * Indicates if a form submission had validation errors. Default is true
	 * @return Boolean true if the form was processed successfully
	 */
	public Boolean isSuccess() {
		return success;
	}

	/**
	 * @return the String validation error message, will be null if validation
	 *         has not been done or validation was successful.
	 */
	public String getValidationError() {
		return validationError;
	}

	/**
	 * Sets the form validation error message which is generated during
	 * validation (i.e. execution of the validation rules).
	 * It also sets the success flag to false.
	 * 
	 * @param validationError String error message
	 */
	public void setValidationError(String validationError) {
		this.success = false;
		this.validationError = validationError;
	}
}
