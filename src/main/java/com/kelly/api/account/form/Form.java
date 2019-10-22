package com.kelly.api.account.form;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.kelly.api.account.controller.http.AccountRequest;

@Component
public abstract class Form {

	public abstract void setFields(AccountRequest request);
	protected abstract boolean validate();
	public abstract ResponseEntity<?> process();
}
