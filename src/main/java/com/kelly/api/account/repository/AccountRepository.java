package com.kelly.api.account.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kelly.api.account.entity.Account;
import com.kelly.api.account.security.Password;

@Repository
public class AccountRepository {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;
	
	public Account findById(Long id) {
		return  em.find(Account.class, id);
	}
	
	public Account findByEmail(String email) {
		
		Account account = new Account();
		TypedQuery<Account> namedQuery = null;
		
		try {
			namedQuery = em.createNamedQuery("get_account_by_email", Account.class);
			account = namedQuery.setParameter("email", email).getSingleResult();
		} catch(NoResultException e) {
			logger.warn("Unable to find an account for email -> {}", email);
		}
		
		return account;
	}
	
	public Account save(Account account) {
		if(account.getId() == null) {
			//insert
			em.persist(account);
		} else {
			//update
			em.merge(account);
		}
		
		return account;
	}
	
	public void deleteById(Long id) {
		Account account = findById(id);
		em.remove(account);
	}
	
	public boolean authenticate(String email, String password) {
		Account account = findByEmail(email);
		String hash = account.getHash();
		if (Password.validPassword(password, hash)) {
			logger.info("Authentication successful!");
			return true;
		} else {
			logger.info("Authentication failed!");
			return false;
		}
	}
}