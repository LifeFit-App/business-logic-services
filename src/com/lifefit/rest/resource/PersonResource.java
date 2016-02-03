package com.lifefit.rest.resource;


import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.lifefit.rest.client.ss.LifeFitSSClient;
import com.lifefit.rest.model.Person;

public class PersonResource {
	@Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;
    
    public PersonResource(UriInfo uriInfo, Request request,int id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }
    
    // Application integration
    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Person getPerson() {    	
    	LifeFitSSClient client = new LifeFitSSClient();    	
        Person person = client.readPerson(id);             
        if (person == null){
        	System.out.println("Get: Person with " + id + " not found");    
        	throw new NotFoundException();
        }        
        return person;
    }
}
