package com.cortez.samples.javaee7angular.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.cortez.samples.javaee7angular.data.Person;
import com.cortez.samples.javaee7angular.pagination.PaginatedListWrapper;

/**
 * REST Service to expose the data to display in the UI grid.
 *
 * @author Roberto Cortez
 */
@Stateless
@Path("persons")
public class PersonResource {

	@Inject
    private EntityManager entityManager;

    private Integer countPersons() {
        Query query = entityManager.createQuery("SELECT COUNT(p.id) FROM Person p");
        return ((Long) query.getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    private List<Person> findPersons(int startPosition, int maxResults, String sortFields, String sortDirections) {
        Query query = entityManager.createQuery("SELECT p FROM Person p ORDER BY p." + sortFields + " " + sortDirections);
        query.setFirstResult(startPosition);
        query.setMaxResults(maxResults);
        return query.getResultList();

        /*
        List<Person> persons = query.getResultList();
        System.out.println("-------------");
        for (Person p : persons) {
        	System.out.printf(
        		"%d) %s's worst enemy: %s\n",
        		p.getId(),
        		p.getName(),
        		(p.getWorstEnemy() != null ? p.getWorstEnemy().getClass().getName() : "null")
        	);
        }
        return persons;
        */
    }

    private PaginatedListWrapper<Person> findPersons(PaginatedListWrapper<Person> wrapper) {
        wrapper.setTotalResults(countPersons());
        int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
        wrapper.setList(findPersons(start,
                                    wrapper.getPageSize(),
                                    wrapper.getSortFields(),
                                    wrapper.getSortDirections()));
        return wrapper;
    }

    @GET
    public PaginatedListWrapper<Person> listPersons(@DefaultValue("1")
                                                    @QueryParam("page")
                                                    Integer page,
                                                    @DefaultValue("id")
                                                    @QueryParam("sortFields")
                                                    String sortFields,
                                                    @DefaultValue("asc")
                                                    @QueryParam("sortDirections")
                                                    String sortDirections) {
        PaginatedListWrapper<Person> paginatedListWrapper = new PaginatedListWrapper<>();
        paginatedListWrapper.setCurrentPage(page);
        paginatedListWrapper.setSortFields(sortFields);
        paginatedListWrapper.setSortDirections(sortDirections);
        paginatedListWrapper.setPageSize(10);
        return findPersons(paginatedListWrapper);
    }

    @GET
    @Path("{id}")
    public Person getPerson( @PathParam("id") Long id) {
        return entityManager.find(Person.class, id);
    }

    @POST
    public Person savePerson(Person person) {
    	return entityManager.merge(person);

    	/*    	
        if (person.getId() == null) {
            Person personToSave = new Person();
            personToSave.setName(person.getName());
            personToSave.setDescription(person.getDescription());
            personToSave.setImageUrl(person.getImageUrl());
            entityManager.persist(person);
        } else {
            Person personToUpdate = getPerson(person.getId());
            personToUpdate.setName(person.getName());
            personToUpdate.setDescription(person.getDescription());
            personToUpdate.setImageUrl(person.getImageUrl());
            person = entityManager.merge(personToUpdate);
        }

        return person;
        */
    }

    @DELETE
    @Path("{id}")
    public void deletePerson(@PathParam("id") Long id) {
        entityManager.remove(getPerson(id));
    }
}
