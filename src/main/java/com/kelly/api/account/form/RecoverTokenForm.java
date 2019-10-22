package com.kelly.api.account.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.kelly.api.account.controller.http.AccountRequest;
import com.kelly.api.account.entity.Account;
import com.kelly.api.account.entity.Token;
import com.kelly.api.account.form.field.TokenField;

@Component
public class RecoverTokenForm extends Form {
	
	@Autowired
	TokenField token;

	@Override
	public void setFields(AccountRequest request) {
		this.token.setValue(request.getToken());
	}

	@Override
	protected boolean validate() {
		boolean valid = true;
		
		this.token.setValidatorRequired();
		if (!this.token.validate()) valid = false;
		
		return valid;
	}

	@Override
	public ResponseEntity<?> process() {
		
		if (validate()) {
			Token token = this.token.getToken();
			Account account = token.getAccount();
			
			return new ResponseEntity<Account>(account, HttpStatus.OK);
		}

		return new ResponseEntity<Account>(new Account(), HttpStatus.UNAUTHORIZED);
	}
}
