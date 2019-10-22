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
import com.kelly.api.account.entity.Token;
import com.kelly.api.account.form.field.EmailField;
import com.kelly.api.account.form.field.PasswordField;
import com.kelly.api.account.repository.AccountRepository;
import com.kelly.api.account.repository.TokenRepository;
import com.kelly.api.account.security.Password;

@Component
public class LoginForm extends Form {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EmailField email;
	
	@Autowired
	PasswordField password;
	
	@Autowired
	AccountRepository ar;
	
	@Autowired
	TokenRepository tr;

	@Override
	public void setFields(AccountRequest request) {
		this.email.setValue(request.getEmail());
		this.password.setValue(request.getPassword());
	}

	@Override
	protected boolean validate() {
		boolean valid = true;
		this.email.setValidatorRequired();
		if (!this.email.validate()) valid = false;
		
		this.password.setValidatorRequired();
		if (!this.password.validate()) valid = false;
		
		return valid;
	}

	@Override
	@Transactional
	public ResponseEntity<?> process() {
		logger.info("processing request.");
		Account account = new Account();
		
		if (validate()) {
			logger.info("Loginform is valid");
			account = ar.findByEmail(email.getValue());
			if (Password.validPassword(password.getValue(), account.getHash())) {
				
				Token token = new Token();
				token.setAccount(account);
				token.setExpiry(60);
				token.setToken();
				
				token = tr.save(token);
				
				account.setToken(token);
				
				return new ResponseEntity<Account>(account, HttpStatus.OK);
			} else {
				account = new Account();
			}
			
		}
		
		return new ResponseEntity<Account>(account, HttpStatus.UNAUTHORIZED);
	}

}
