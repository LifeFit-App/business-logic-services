package com.lifefit.rest.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.lifefit.rest.model.Goal;
import com.lifefit.rest.model.HealthMeasureHistory;
import com.lifefit.rest.model.LifeStatus;
import com.lifefit.rest.model.Person;
import com.lifefit.rest.model.Measure;

import java.io.InputStream;

public class Transformer {

	public Person unmarshallJSONPerson(InputStream stream){		
		Person person = null;		
		try{								
	        // Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);					
			person = mapper.readValue(stream, Person.class);			
		}
		catch(Exception e){e.printStackTrace();}		
		return person;
	}
	
	public Measure[] unmarshallJSONMeasureTypes(InputStream stream){
		Measure[] measureTypes = null;
		try{
			// Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);					
			measureTypes = mapper.readValue(stream, Measure[].class);	
		}
		catch(Exception e){e.printStackTrace();}
		return measureTypes;
	}
	
	public Measure unmarshallJSONMeasure(InputStream stream){
		Measure measureTypes = null;
		try{
			// Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);					
			measureTypes = mapper.readValue(stream, Measure.class);	
		}
		catch(Exception e){e.printStackTrace();}
		return measureTypes;
	}
	
	public Goal unmarshallJSONGoal(InputStream stream){
		Goal personGoal = null;
		try{
			// Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);					
			personGoal = mapper.readValue(stream, Goal.class);	
		}
		catch(Exception e){e.printStackTrace();}
		return personGoal;
	}
	
	public LifeStatus unmarshallJSONLifeStatus(InputStream stream){
		LifeStatus lifeStatus = null;
		try{
			// Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);					
			lifeStatus = mapper.readValue(stream, LifeStatus.class);	
		}
		catch(Exception e){e.printStackTrace();}
		return lifeStatus;
	}
	
	public HealthMeasureHistory[] unmarshallJSONHealthMeasureHistory(InputStream stream){
		HealthMeasureHistory[] personHealthMeasureHistory = null;
		try{
			// Jackson Object Mapper
			ObjectMapper mapper = new ObjectMapper();			
			// Adding the Jackson Module to process JAXB annotations
			JaxbAnnotationModule module = new JaxbAnnotationModule();			
			//Mapper configuration
			mapper.registerModule(module);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);					
			personHealthMeasureHistory = mapper.readValue(stream, HealthMeasureHistory[].class);	
		}
		catch(Exception e){e.printStackTrace();}
		return personHealthMeasureHistory;
	}
}
