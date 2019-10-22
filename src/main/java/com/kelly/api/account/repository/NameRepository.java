package com.kelly.api.account.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kelly.api.account.entity.Name;

@Repository
public class NameRepository {

	@Autowired
	EntityManager em;
	
	public Name findById(Long id) {
		return em.find(Name.class, id);
	}
	
	public Name save(Name name) {
		if(name.getId() == null) {
			//insert
			em.persist(name);
		} else {
			//update
			em.merge(name);
		}
		
		return name;
	}
	
	public void deleteById(Long id) {
		Name name = findById(id);
		em.remove(name);
	}
}
