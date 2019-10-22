package com.kelly.api.account.controller.http;

public class AccountRequest {

	private String email;
	private String password;
	private String oldPassword;
	private String displayName;
	private String firstName;
	private String lastName;
	private String token;
	
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public String getDisplayName() {
		return displayName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
