package com.kelly.api.account.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@NamedQueries(
	{
		@NamedQuery(name="get_token_by_token", 
			query="select t from Token t where t.token=:token and t.expiry >=:expiry")
	}
)
public class Token {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;
	
	@Column(nullable = false, length=32)
	private String token;
	
	@Column(nullable = false)
	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiry;
	
	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY, optional=true)
	private Account account;

	
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken() {
		int length = 32;
        boolean useLetters = true;
        boolean useNumbers = true;
        this.token = RandomStringUtils.random(length, useLetters, useNumbers);
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiry() {
		return expiry;
	}
	
	public void setExpiry(int minutes) {
		Date date = new Date(System.currentTimeMillis()+minutes*60*1000);
        Timestamp timestamp = new Timestamp(date.getTime());
        this.expiry = timestamp;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
