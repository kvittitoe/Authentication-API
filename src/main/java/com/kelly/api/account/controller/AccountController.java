package com.kelly.api.account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kelly.api.account.controller.http.AccountRequest;
import com.kelly.api.account.form.ChangePasswordForm;
import com.kelly.api.account.form.LoginForm;
import com.kelly.api.account.form.RecoverEmailForm;
import com.kelly.api.account.form.RecoverTokenForm;
import com.kelly.api.account.form.RegisterForm;
import com.kelly.api.account.form.UpdateAccountForm;
import com.kelly.api.account.form.UpdateNameForm;

@Component
@RestController
public class AccountController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	RegisterForm registerForm;
	
	@Autowired
	LoginForm loginForm;
	
	@Autowired
	RecoverEmailForm recoverEmailForm;
	
	@Autowired
	RecoverTokenForm recoverTokenForm;
	
	@Autowired
	ChangePasswordForm changePasswordForm;
	
	@Autowired
	UpdateAccountForm updateAccount;
	
	@Autowired
	UpdateNameForm updateName;

	@CrossOrigin
	@RequestMapping(value = "/account/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestBody AccountRequest request) {
		logger.info("[POST] [/account/register] registering new user");
		logger.info("Email -> {}", request.getEmail());
		logger.info("Display name -> {}", request.getDisplayName());
		logger.info("First name -> {}", request.getFirstName());
		logger.info("Last name -> {}", request.getLastName());
		
		registerForm.setFields(request);
		
		return registerForm.process();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/account/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody AccountRequest request) {
		logger.info("[POST] [/account/login] login attempt for user: {}", request.getEmail());
		logger.info("Email -> {}", request.getEmail());
		loginForm.setFields(request);
		return loginForm.process();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/account/recover", method = RequestMethod.POST)
	public ResponseEntity<?> recover(@RequestBody AccountRequest request) {
		logger.info("[POST] [/account/recover] recover attempt for account: {}", request.getEmail());
		recoverEmailForm.setFields(request);
		return recoverEmailForm.process();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/account/recover/token", method = RequestMethod.GET)
	public ResponseEntity<?> emailExists(@RequestParam(name = "token") String token) {
		logger.info("[GET] [/account/recover/token] recover account with token: {}", token);
		AccountRequest request = new AccountRequest();
		request.setToken(token);
		this.recoverTokenForm.setFields(request);
		return this.recoverTokenForm.process();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/account/recover/token", method = RequestMethod.POST)
	public ResponseEntity<?> recoverByToken(@RequestBody AccountRequest request) {
		logger.info("[POST] [/account/token] recover attempt for account: {}", request.getToken());
		this.changePasswordForm.setFields(request);
		return this.changePasswordForm.process();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/account/update/account", method = RequestMethod.POST)
	public ResponseEntity<?> updateAccount(@RequestBody AccountRequest request) {
		logger.info("[POST] [/account/update/account] updating information for account: {}", request.getToken());
		this.updateAccount.setFields(request);
		return this.updateAccount.process();
	}
	
	@CrossOrigin
	@RequestMapping(value = "/account/update/name", method = RequestMethod.POST)
	public ResponseEntity<?> updateName(@RequestBody AccountRequest request) {
		logger.info("[POST] [/account/update/name] updating information for name: {}", request.getToken());
		this.updateName.setFields(request);
		return this.updateName.process();
	}
}
