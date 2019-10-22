package com.kelly.api.account.form;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.kelly.api.account.controller.http.AccountRequest;
import com.kelly.api.account.entity.Account;
import com.kelly.api.account.entity.Name;
import com.kelly.api.account.entity.Token;
import com.kelly.api.account.form.error.FormError;
import com.kelly.api.account.form.error.PropertyError;
import com.kelly.api.account.form.field.FirstNameField;
import com.kelly.api.account.form.field.LastNameField;
import com.kelly.api.account.form.field.TokenField;
import com.kelly.api.account.repository.NameRepository;
import com.kelly.api.account.repository.TokenRepository;

@Component
public class UpdateNameForm extends Form implements ErrorResponse {

	@Autowired
	FirstNameField firstName;
	
	@Autowired
	LastNameField lastName;
	
	@Autowired
	TokenField token;
	
	@Autowired
	NameRepository nr;
	
	@Autowired
	TokenRepository tr;

	@Override
	public void setFields(AccountRequest request) {
		this.firstName.setValue(request.getFirstName());
		this.lastName.setValue(request.getLastName());
		this.token.setValue(request.getToken());
	}

	@Override
	protected boolean validate() {
		boolean valid = true;
		
		this.firstName.setValidatorRequired();
		this.firstName.setValidatorMinLength(2);
		this.firstName.setValidatorMaxLength(30);
		this.firstName.setValidatorName();
		if (!this.firstName.validate()) valid = false;
		
		this.lastName.setValidatorRequired();
		this.lastName.setValidatorMinLength(2);
		this.lastName.setValidatorMaxLength(30);
		this.lastName.setValidatorName();
		if (!this.lastName.validate()) valid = false;
		
		this.token.setValidatorRequired();
		if (!this.token.validate()) valid = false;
		
		return valid;
	}

	@Override
	@Transactional
	public ResponseEntity<?> process() {

		if (validate()) {
			Token token = tr.findByToken(this.token.getValue());
			Account account = token.getAccount();
			Name name = account.getName();
			name.setFirstName(this.firstName.getValue());
			name.setLastName(this.lastName.getValue());
			name = nr.save(name);
			
			account.setName(name);
			
			return new ResponseEntity<Account>(account, HttpStatus.OK);
		}
		return new ResponseEntity<FormError>(getFormError(), HttpStatus.OK);
	}
	
	public FormError getFormError() {
		PropertyError firstNameError = new PropertyError(this.firstName.getLabel().getName(), this.firstName.getErrors());
		PropertyError lastNameError = new PropertyError(this.lastName.getLabel().getName(), this.lastName.getErrors());
		
		FormError formError = new FormError();
		formError.addFieldError(firstNameError);
		formError.addFieldError(lastNameError);
		
		return formError;
	}
}
