package com.kelly.api.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Name {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;
	
	@Column(nullable=false, length=30)
	private String firstName;
	
	@Column(nullable=false, length=30)
	private String lastName;
	
	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY)
	private Account account;
	
	public Name() {}
	
	public Name(String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
