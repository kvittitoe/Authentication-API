package com.kelly.api.account.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.kelly.api.account.controller.http.AccountRequest;
import com.kelly.api.account.entity.Account;
import com.kelly.api.account.form.error.FormError;
import com.kelly.api.account.form.error.PropertyError;
import com.kelly.api.account.form.field.EmailField;
import com.kelly.api.account.security.RecoveryEmail;

@Component
public class RecoverEmailForm extends Form implements ErrorResponse {

	@Autowired
	EmailField email;
	
	@Override
	public void setFields(AccountRequest request) {
		this.email.setValue(request.getEmail());
	}

	@Override
	protected boolean validate() {
		this.email.setValidatorRequired();
		this.email.setValidatorEmail();
		return this.email.validate();
	}

	@Override
	public ResponseEntity<?> process() {

		if (validate()) {
			RecoveryEmail sendEmail = new RecoveryEmail(email.getValue());
			
			return new ResponseEntity<Account>(new Account(), HttpStatus.OK);
		}
		return new ResponseEntity<FormError>(getFormError(), HttpStatus.OK);
	}

	@Override
	public FormError getFormError() {
		PropertyError emailError = new PropertyError(this.email.getLabel().getName(), this.email.getErrors());
		FormError formError = new FormError();
		formError.addFieldError(emailError);
		return formError;
	}
}
