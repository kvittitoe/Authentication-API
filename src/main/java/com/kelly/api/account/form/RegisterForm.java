package com.kelly.api.account.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kelly.api.account.controller.http.AccountRequest;
import com.kelly.api.account.entity.Account;
import com.kelly.api.account.entity.Name;
import com.kelly.api.account.form.error.FormError;
import com.kelly.api.account.form.error.PropertyError;
import com.kelly.api.account.form.field.DisplayNameField;
import com.kelly.api.account.form.field.EmailField;
import com.kelly.api.account.form.field.FirstNameField;
import com.kelly.api.account.form.field.LastNameField;
import com.kelly.api.account.form.field.PasswordField;
import com.kelly.api.account.repository.AccountRepository;
import com.kelly.api.account.repository.NameRepository;

@Component
public class RegisterForm extends Form implements ErrorResponse {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EmailField email;
	
	@Autowired
	PasswordField password;
	
	@Autowired
	DisplayNameField displayName;
	
	@Autowired
	FirstNameField firstName;
	
	@Autowired 
	LastNameField lastName;
	
	@Autowired
	AccountRepository ar;
	
	@Autowired
	NameRepository nr;
	
	public RegisterForm() {	}
	
	@Override
	public void setFields(AccountRequest request) {
		this.email.setValue(request.getEmail());
		this.password.setValue(request.getPassword());
		this.displayName.setValue(request.getDisplayName());
		this.firstName.setValue(request.getFirstName());
		this.lastName.setValue(request.getLastName());
	}

	@Override
	protected boolean validate() {
		boolean valid = true;
		
		this.email.setValidatorRequired();
		this.email.setValidatorEmail();
		if (!this.email.validate()) valid = false;
		
		this.password.setValidatorRequired();
		this.password.setValidatorMinLength(8);
		this.password.setValidatorPassword();
		if (!this.password.validate()) valid = false;
		
		this.displayName.setValidatorRequired();
		this.displayName.setValidatorMinLength(6);
		this.displayName.setValidatorMaxLength(30);
		this.displayName.setSetValidatorCharactersAndNumbers();
		if (!this.displayName.validate()) valid = false;
		
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
		
		if (valid) {
			logger.info("Register form is valid.");
		} else {
			logger.info("Register form is invalid.");
		}
		
		return valid;
	}

	@Override
	@Transactional
	public ResponseEntity<?> process() {
		
		if (validate()) {
			
			Account account = new Account(this.email.getValue(), this.password.getValue(), this.displayName.getValue());
			account = ar.save(account);
			
			Name name = new Name(this.firstName.getValue(), this.lastName.getValue());
			name.setAccount(account);
			name = nr.save(name);
			
			account.setName(name);
			
			return new ResponseEntity<Account>(account, HttpStatus.CREATED);	
		}
		return new ResponseEntity<FormError>(getFormError(), HttpStatus.OK);
	}
	
	public FormError getFormError() {
		PropertyError emailError = new PropertyError(this.email.getLabel().getName(), this.email.getErrors());
		PropertyError passwordError = new PropertyError(this.password.getLabel().getName(), this.password.getErrors());
		PropertyError displayNameError = new PropertyError(this.displayName.getLabel().getName(), this.displayName.getErrors());
		PropertyError firstNameError = new PropertyError(this.firstName.getLabel().getName(), this.firstName.getErrors());
		PropertyError lastNameError = new PropertyError(this.lastName.getLabel().getName(), this.lastName.getErrors());
		
		FormError formError = new FormError();
		formError.addFieldError(emailError);
		formError.addFieldError(passwordError);
		formError.addFieldError(displayNameError);
		formError.addFieldError(firstNameError);
		formError.addFieldError(lastNameError);
		
		return formError;
	}
}
