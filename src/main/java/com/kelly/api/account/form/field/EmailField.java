package com.kelly.api.account.form.field;

import org.springframework.stereotype.Component;

@Component
public class EmailField extends Field {
	
	/*Field Validator Properties*/
	private boolean validatorEmail = false;
	
	public EmailField() {
		super(FieldLabel.EMAIL);
	}

	@Override
	public boolean validate() {
		
		boolean valid = true;
		
		if (!isRequired()) return false;
		
		if (validatorEmail) {
			if (!isValidPattern(FieldError.EMAIL)) valid = false;
		}
		
		return valid;
	}
	
	/*Generic Validators*/
	public void setValidatorEmail() {
		this.validatorEmail = true;
	}
}
