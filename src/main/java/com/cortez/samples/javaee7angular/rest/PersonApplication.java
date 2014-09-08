package com.cortez.samples.javaee7angular.rest;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.spearal.DefaultSpearalFactory;
import org.spearal.SpearalFactory;
import org.spearal.jpa2.SpearalConfigurator;

@ApplicationPath("/resources")
public class PersonApplication extends Application {
	
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;
	
	@Produces
	private SpearalFactory spearalFactory = new DefaultSpearalFactory();
	
	@PostConstruct
	public void init() {
		SpearalConfigurator.init(spearalFactory, entityManagerFactory);
	}
}
