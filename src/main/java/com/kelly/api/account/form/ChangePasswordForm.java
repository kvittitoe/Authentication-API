package com.kelly.api.account.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.kelly.api.account.controller.http.AccountRequest;
import com.kelly.api.account.entity.Account;
import com.kelly.api.account.entity.Token;
import com.kelly.api.account.form.field.PasswordField;
import com.kelly.api.account.form.field.TokenField;
import com.kelly.api.account.repository.AccountRepository;
import com.kelly.api.account.repository.TokenRepository;

@Component
public class ChangePasswordForm extends Form {
	
	@Autowired
	PasswordField password;
	
	@Autowired
	PasswordField oldPassword;
	
	@Autowired
	TokenField token;
	
	@Autowired
	AccountRepository ar;
	
	@Autowired
	TokenRepository tr;
	
	private boolean requirePassword;

	@Override
	public void setFields(AccountRequest request) {
		this.password.setValue(request.getPassword());
		this.token.setValue(request.getToken());
	}
	
	public void setFields(AccountRequest request, boolean requirePassword) {
		this.requirePassword = requirePassword;
		setFields(request);
		if (requirePassword) {
			this.oldPassword.setValue(request.getOldPassword());
		}
	}

	@Override
	protected boolean validate() {
		boolean valid = true;
		
		if (requirePassword) {
			this.oldPassword.setValidatorRequired();
			if (!this.oldPassword.validate()) valid = false;
		}
		this.password.setValidatorRequired();
		this.password.setValidatorMinLength(8);
		this.password.setValidatorPassword();
		if (!this.token.validate()) valid = false;
		
		this.token.setValidatorRequired();
		if (!this.token.validate()) valid = false;
		
		return valid;
	}

	@Override
	public ResponseEntity<?> process() {

		if (validate()) {
			Token token = this.tr.findByToken(this.token.getValue());
			Account account = token.getAccount();
			account.setHash(this.password.getValue());
			account = ar.save(account);
			
			return new ResponseEntity<Account>(account, HttpStatus.OK);
		}
		return new ResponseEntity<Account>(new Account(), HttpStatus.UNAUTHORIZED);
	}
}
