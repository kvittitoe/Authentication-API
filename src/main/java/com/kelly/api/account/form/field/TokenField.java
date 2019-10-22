package com.kelly.api.account.form.field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kelly.api.account.entity.Token;
import com.kelly.api.account.repository.TokenRepository;

@Component
public class TokenField extends Field {
	
	@Autowired
	TokenRepository tr;
	
	private Token token;

	public TokenField() {
		super(FieldLabel.TOKEN);
	}

	@Override
	public boolean validate() {
		boolean valid = true;
		
		if (!isRequired()) return false;
		
		this.token = tr.findByToken(getValue());	
		if (token.getId() == null) {
			valid = false;
			addError(FieldError.TOKEN);
		}
		
		return valid;
	}
	
	public Token getToken() {
		return token;
	}
}
