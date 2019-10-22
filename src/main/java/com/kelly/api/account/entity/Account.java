package com.kelly.api.account.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kelly.api.account.security.Password;

@Entity
@NamedQueries(
		{
			@NamedQuery(name="get_account_by_email", 
				query="select a from Account a "
						+ "where a.email=:email"
			)
		}
)
public class Account {

	@Id
	@GeneratedValue
	private Long id;
	
	
	
	@Column(nullable=false)
	private String email;
	
	@Column(nullable=false)
	@JsonIgnore
	private String hash;
	
	@Column(nullable=false, length=30)
	private String displayName;
	
	@Column(nullable=false, length=1)
	@JsonIgnore
	private String active;
	
	@CreationTimestamp
	@JsonIgnore
	@Column
    private LocalDateTime creationDate;

	@OneToOne(mappedBy="account", fetch = FetchType.LAZY)
	Name name;
	
	@OneToOne(mappedBy="account", fetch = FetchType.LAZY)
	Token token;
	
	public Account() {
		setActive();
	}
	
	public Account(String email, String password, String displayName) {
		setEmail(email);
		setHash(password);
		setDisplayName(displayName);
		setActive();
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String password) {
		this.hash = Password.hash(password);
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getActive() {
		return active;
	}

	public void setActive() {
		this.active = "Y";
	}
	
	public void setInActive() {
		this.active = "N";
	}

	public LocalDateTime getCreateDate() {
		return creationDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.creationDate = createDate;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
}
