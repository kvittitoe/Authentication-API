package com.kelly.api.account.form.field;

import org.springframework.stereotype.Component;

@Component
public class PasswordField extends Field {
	
	/*Field Validator Properties*/
	private boolean validatorPassword = false;

	public PasswordField() {
		super(FieldLabel.PASSWORD);
	}

	@Override
	public boolean validate() {
		
		boolean valid = true;
		
		if (!isRequired()) return false;
		if (!isMinLength()) valid = false;
		
		if (validatorPassword) {
			if (!isValidPattern(FieldError.SPECIAL)) valid = false;
			if (!isValidPattern(FieldError.LOWER)) valid = false;
			if (!isValidPattern(FieldError.UPPER)) valid = false;
			if (!isValidPattern(FieldError.NUMBER)) valid = false;
		}
		
		return valid;
	}
	
	/*Generic Validators*/
	public void setValidatorPassword() {
		this.validatorPassword = true;
	}
}
