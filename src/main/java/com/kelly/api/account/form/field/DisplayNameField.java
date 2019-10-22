package com.kelly.api.account.form.field;

import org.springframework.stereotype.Component;

@Component
public class DisplayNameField extends Field {
	
	private boolean isValidatorCharactersAndNumbers = false;

	public DisplayNameField() {
		super(FieldLabel.DISPLAY_NAME);
	}

	@Override
	public boolean validate() {
		boolean valid = true;
		
		if (!isRequired()) return false;
		if (!isMaxLength()) valid = false;
		if (!isMinLength()) valid = false;
		
		if (isValidatorCharactersAndNumbers) {
			FieldError error = FieldError.LETTERS_NUMBERS_ONLY;
			if (!this.isValidPattern(error)) {
				valid = false;
			}
		}
		
		return valid;
	}

	public void setSetValidatorCharactersAndNumbers() {
		this.isValidatorCharactersAndNumbers = true;
	}
}
