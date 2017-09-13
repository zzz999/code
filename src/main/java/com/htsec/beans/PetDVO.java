package com.htsec.beans;

import java.io.Serializable;
import java.util.Date;

public class PetDVO implements Serializable {
	
	private static final long serialVersionUID = -3590085190381733973L;
	
	
	private String name;
	private String owner;
	private String species;
	private String sex;
	private Date birth;
	private Date death;
	
	// Add getter and setter methods
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public Date getDeath() {
		return death;
	}
	public void setDeath(Date death) {
		this.death = death;
	}
	
	
}
