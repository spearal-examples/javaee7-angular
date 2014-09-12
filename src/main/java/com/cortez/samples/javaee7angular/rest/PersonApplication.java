package com.cortez.samples.javaee7angular.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.spearal.DefaultSpearalFactory;
import org.spearal.SpearalFactory;
import org.spearal.jpa2.EntityManagerWrapper;
import org.spearal.jpa2.SpearalConfigurator;

@ApplicationPath("/resources")
public class PersonApplication extends Application {

	@Produces @PersistenceContext
	private EntityManager entityManager;

//	@PersistenceContext
//	private EntityManager entityManager;
//	
//	@Produces
//	public EntityManager getEntityManager() {
//		return new EntityManagerWrapper(entityManager);
//	}
//	
//	@PersistenceUnit
//	private EntityManagerFactory entityManagerFactory;
//	
//	@Produces @ApplicationScoped
//	public SpearalFactory getSpearalFactory() {
//		SpearalFactory spearalFactory = new DefaultSpearalFactory();
//		SpearalConfigurator.init(spearalFactory, entityManagerFactory);
//		return spearalFactory;
//	}
}
