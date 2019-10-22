package com.kelly.api.account.form.error;

import java.util.ArrayList;
import java.util.List;

public class FormError {

	List<PropertyError> errors;
	
	public FormError() {
		this.errors = new ArrayList<>();
	}
	
	public void addFieldError(PropertyError error) {
		this.errors.add(error);
	}

	public List<PropertyError> getErrors() {
		return errors;
	}
}
