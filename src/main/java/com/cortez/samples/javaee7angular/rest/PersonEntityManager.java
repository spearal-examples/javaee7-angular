package com.cortez.samples.javaee7angular.rest;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PersonEntityManager {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Produces
	public EntityManager getEntityManager() {
		return entityManager;
	}
}