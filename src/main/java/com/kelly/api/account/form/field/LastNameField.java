package com.kelly.api.account.form.field;

import org.springframework.stereotype.Component;

@Component
public class LastNameField extends Field {
	
	/*Field Validator Properties*/
	private boolean validatorName = false;

	public LastNameField() {
		super(FieldLabel.LAST_NAME);
	}

	@Override
	public boolean validate() {
		
		boolean valid = true;
		
		if (!isRequired()) return false;
		if (!isMinLength()) valid = false;
		if (!isMaxLength()) valid = false;
		
		if (validatorName) {
			if (!isValidPattern(FieldError.LETTERS_ONLY)) valid = false;
		}
		
		return valid;
	}
	
	/*Generic Validators*/
	public void setValidatorName() {
		this.validatorName = true;
	}
}
