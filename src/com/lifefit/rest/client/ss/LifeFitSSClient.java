package com.lifefit.rest.client.ss;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import java.security.MessageDigest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;

import org.glassfish.jersey.client.ClientConfig;

import com.lifefit.rest.model.Goal;
import com.lifefit.rest.model.HealthMeasureHistory;
import com.lifefit.rest.model.LifeStatus;
import com.lifefit.rest.model.Measure;
import com.lifefit.rest.model.Person;
import com.lifefit.rest.util.Transformer;
import com.owlike.genson.ext.jaxrs.GensonJsonConverter;

public class LifeFitSSClient {

	static InputStream stream = null;
	
	static Response response;
	static String results = null;
	//RESTFul Web Service URL for LifeFit storage services
	final String SERVER_URL = "http://localhost:5700/lifefit-ss";
	WebTarget service;
	
	public LifeFitSSClient(){
		init();
	}
	
	private void init(){
		final ClientConfig clientConfig = new ClientConfig().register(GensonJsonConverter.class);			
		Client client = ClientBuilder.newClient(clientConfig);
		service = client.target(getBaseURI(SERVER_URL));		
	}
	
	private static URI getBaseURI(String SERVER_URL){
		return UriBuilder.fromUri(SERVER_URL).build();
	}
	
	public Person readPerson(int personId){
		Person person = new Person();
		
		try{
			response = service.path("person/"+personId).request().accept(MediaType.APPLICATION_JSON).get();
			results = response.readEntity(String.class);			
			//Convert string into inputStream
			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
			
			Transformer transform = new Transformer();
			person = transform.unmarshallJSONPerson(stream);								
		}
		catch(Exception e){e.printStackTrace();}
		return person;
	}
	
	public boolean updatePersonGoal(Goal goal, int personId, String measureName){				
		boolean result = false;
		int httpStatus = 0;
		
		try{
			//Get Measure by measureName
			Measure measure = getMeasureByName(measureName);
			//Set goal with the corresponding idMeasure based on the given measureName
			goal.setMeasure(measure);
			//Get person by given personId
			Person person = readPerson(personId);
			//Set goal with the corresponding idMeasure based on the given measureName
			goal.setPerson(person);
			
			//Update goal
			response = service.path("person/"+personId+"/goal").request(MediaType.APPLICATION_JSON)
					.put(Entity.entity(goal, MediaType.APPLICATION_JSON), Response.class);
			httpStatus = response.getStatus();
			
			if(httpStatus == 200 || httpStatus == 201)
				result = true;
		}
		catch(Exception e){e.printStackTrace();}
		return result;
	}
	
	public boolean savePersonHealthMeasure(LifeStatus lifeStatus, int personId, String measureName){		
		boolean result = false;
		int httpStatus = 0;
		int idMeasure = 0;
		
		try{
			//Get Measure by measureName
			Measure measure = getMeasureByName(measureName);
			idMeasure = measure.getIdMeasure();			
			//Set lifeStatus with the corresponding idMeasure based on the given measureName
			lifeStatus.setMeasure(measure);
			
			//Get person by given personId
			Person person = readPerson(personId);
			//Get existing lifeStatus with the same measureName for the corresponding person
			LifeStatus currentLifeStatus = getLifeStatusByPersonAndMeasureId(personId, idMeasure);
			
			if(currentLifeStatus != null){
				//Save currentLifeStatus into HealthMeasureHistory Table by creating new HealthMeasureHistory
				HealthMeasureHistory healthMeasure = new HealthMeasureHistory();
				healthMeasure.setPerson(person);
				healthMeasure.setMeasure(measure);
				healthMeasure.setValue(currentLifeStatus.getValue());
				healthMeasure.setDatetime(new Date());
				
				service.path("person/"+personId+"/measurehistory").request(MediaType.APPLICATION_JSON)
						.post(Entity.entity(healthMeasure, MediaType.APPLICATION_JSON), Response.class);
		
				//Delete currentLifeStatus from LifeStatus table
				service.path("person/"+personId+"/hp").request().delete();					
			}
			
			//Create a new LifeStatus for the given personId
			lifeStatus.setPerson(person);			
			response = service.path("person/"+personId+"/hp").request(MediaType.APPLICATION_JSON)
					.post(Entity.entity(lifeStatus, MediaType.APPLICATION_JSON), Response.class);
			httpStatus = response.getStatus();
			
			if(httpStatus == 200 || httpStatus == 201)
				result = true;  
			else
				result = false;
		}
		catch(Exception e){e.printStackTrace();}
		return result;
	}
	
	public HealthMeasureHistory[] getPersonHealthMeasureHistory(int personId, String measureName){
		HealthMeasureHistory[] healthMeasure = null;
		int idMeasure = 0;
		
		try{
			//Get Measure by measureName
			Measure measure = getMeasureByName(measureName);
			idMeasure = measure.getIdMeasure();
			
			response = service.path("person/"+personId+"/measurehistory/"+idMeasure).request().accept(MediaType.APPLICATION_JSON).get();
			results = response.readEntity(String.class);
			//Convert string into inputStream
			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
			
			Transformer transform = new Transformer();
			healthMeasure = transform.unmarshallJSONHealthMeasureHistory(stream);
		}
		catch(Exception e){e.printStackTrace();}
		return healthMeasure;
	}
	
	public Measure getMeasureByName(String measureName){
		Measure measure = null;
		
		try{
			response = service.path("measureTypes/"+measureName).request().accept(MediaType.APPLICATION_JSON).get();
			results = response.readEntity(String.class);
			//Convert string into inputStream
			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
			
			Transformer transform = new Transformer();
			measure = transform.unmarshallJSONMeasure(stream);
		}
		catch(Exception e){e.printStackTrace();}
		return measure;
	}
	
	public LifeStatus getLifeStatusByPersonAndMeasureId(int personId, int measureId){
		LifeStatus lifeStatus = null;
		
		try{
			response = service.path("person/"+personId+"/hp/"+measureId).request().accept(MediaType.APPLICATION_JSON).get();
			results = response.readEntity(String.class);
			//Convert string into inputStream
			stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
			
			Transformer transform = new Transformer();
			lifeStatus = transform.unmarshallJSONLifeStatus(stream);
		}
		catch(Exception e){e.printStackTrace();}
		return lifeStatus;
	}
	
	public Person authenticateUser(String email, String pass){
		Person person = null;
		
		try{
			response = service.path("person/"+email+"/"+MD5(pass)).request().accept(MediaType.APPLICATION_JSON).get();
			results = response.readEntity(String.class);
			if(results != null){
				//Convert string into inputStream
				stream = new ByteArrayInputStream(results.getBytes(StandardCharsets.UTF_8));
				
				Transformer transform = new Transformer();
				person = transform.unmarshallJSONPerson(stream);
			}			
		}
		catch(Exception e){e.printStackTrace();}
		return person;
	}
	
	public String MD5(String md5) {
	   try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] array = md.digest(md5.getBytes());
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < array.length; ++i) {
	          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	        }
	        return sb.toString();
	    } catch (java.security.NoSuchAlgorithmException e) {
	    }
	    return null;
	} 
	
	public static void main(String[] args){
		LifeFitSSClient client = new LifeFitSSClient();
		
		System.out.println(client.authenticateUser("admin", "admin"));
	}
}
