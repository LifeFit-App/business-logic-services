package com.lifefit.rest.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Goal implements Serializable {
	private final static long serialVersionUID = 1L;
	
	@XmlElement
	private int idGoal;
	@XmlElement
	private double goalTarget;
	@XmlElement
	private Measure measure;
	@XmlElement
	private Person person;
	
	public Goal(){}

	public int getIdGoal() {
		return idGoal;
	}

	public void setIdGoal(int idGoal) {
		this.idGoal = idGoal;
	}

	public double getGoalTarget() {
		return goalTarget;
	}

	public void setGoalTarget(double goalTarget) {
		this.goalTarget = goalTarget;
	}

	public Measure getMeasure() {
		return measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
