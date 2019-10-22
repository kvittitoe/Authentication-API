package com.kelly.api.account.repository;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kelly.api.account.entity.Token;

@Repository
public class TokenRepository {

	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;
	
	public Token findByToken(String token) {
		
		Date date = new Date(System.currentTimeMillis());
        Timestamp timestamp = new Timestamp(date.getTime());
		
		Token tokenEntity = new Token();
		
		TypedQuery<Token> namedQuery = null;
		try {
			namedQuery = em.createNamedQuery("get_token_by_token", Token.class);
			tokenEntity =  namedQuery
					.setParameter("token", token)
					.setParameter("expiry", timestamp)
					.getSingleResult();
		} catch(NoResultException e) {
			logger.warn("Unable to find a token for idToken -> {}", token);
		}
		
		return tokenEntity;
	}
	
	public Token save(Token token) {
		if(token.getId() == null) {
			//insert
			em.persist(token);
		} else {
			//update
			em.merge(token);
		}
		
		return token;
	}
	
	public void deleteById(String idToken) {
		Token token = findByToken(idToken);
		em.remove(token);
	}
}
