package com.cortez.samples.javaee7angular.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JacksonContextResolver implements ContextResolver<ObjectMapper> {
    
	private ObjectMapper objectMapper;

    public JacksonContextResolver() throws Exception {
        this.objectMapper = new ObjectMapper().registerModule(new Hibernate4Module());
    }
    public ObjectMapper getContext(Class<?> objectType) {
        return objectMapper;
    }
}