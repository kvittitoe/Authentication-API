package com.kelly.api.account.form;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kelly.api.account.controller.http.AccountRequest;
import com.kelly.api.account.entity.Account;
import com.kelly.api.account.entity.Token;
import com.kelly.api.account.form.error.FormError;
import com.kelly.api.account.form.error.PropertyError;
import com.kelly.api.account.form.field.DisplayNameField;
import com.kelly.api.account.form.field.EmailField;
import com.kelly.api.account.form.field.TokenField;
import com.kelly.api.account.repository.AccountRepository;
import com.kelly.api.account.repository.TokenRepository;

@Component
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UpdateAccountForm extends Form implements ErrorResponse {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EmailField email;
	
	@Autowired
	DisplayNameField displayName;
	
	@Autowired
	TokenField token;
	
	@Autowired
	AccountRepository ar;
	
	@Autowired
	TokenRepository tr;

	@Override
	public void setFields(AccountRequest request) {
		this.email.setValue(request.getEmail());
		this.displayName.setValue(request.getDisplayName());
		this.token.setValue(request.getToken());
	}

	@Override
	protected boolean validate() {
		boolean valid = true;
		
		this.token.setValidatorRequired();
		if (!this.token.validate()) valid = false;
		
		this.email.setValidatorRequired();
		this.email.setValidatorEmail();
		if (!this.email.validate()) valid = false;
		
		this.displayName.setValidatorRequired();
		this.displayName.setSetValidatorCharactersAndNumbers();
		this.displayName.setValidatorMinLength(6);
		this.displayName.setValidatorMaxLength(30);
		if (!this.displayName.validate()) valid = false;
		
		return valid;
	}

	@Override
	@Transactional
	public ResponseEntity<?> process() {

		if (validate()) {
			Token token = tr.findByToken(this.token.getValue());
			Account account = token.getAccount();
			account.setDisplayName(this.displayName.getValue());
			account.setEmail(this.email.getValue());
			account = ar.save(account);
			account.getName();
			account.getToken();
			return new ResponseEntity<Account>(account, HttpStatus.OK);
		}
		
		return new ResponseEntity<FormError>(getFormError(), HttpStatus.OK);
	}
		
	public FormError getFormError() {
		PropertyError emailError = new PropertyError(this.email.getLabel().getName(), this.email.getErrors());
		PropertyError displayNameError = new PropertyError(this.displayName.getLabel().getName(), this.displayName.getErrors());
			
		FormError formError = new FormError();
		formError.addFieldError(emailError);
		formError.addFieldError(displayNameError);
		
		return formError;
	}
}
