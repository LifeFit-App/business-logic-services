package com.lifefit.rest.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Measure implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@XmlElement
	private int idMeasure;
	@XmlElement
	private String measureName;
	@XmlElement
	private String measureType;
	
	public Measure(){}
	
	public Measure(int idMeasure){
		this.idMeasure = idMeasure;
	}
	
	public int getIdMeasure() {
		return idMeasure;
	}
	public void setIdMeasure(int idMeasure) {
		this.idMeasure = idMeasure;
	}
	public String getMeasureName() {
		return measureName;
	}
	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}
	public String getMeasureType() {
		return measureType;
	}
	public void setMeasureType(String measureType) {
		this.measureType = measureType;
	}	
}
