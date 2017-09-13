package com.htsec.beans;

import java.io.Serializable;
import java.util.Date;

public class Pet implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8371242956756561591L;

	private String name;
	private String owner;
	private String species;
	private String sex;
	private String birth;
	private String death;
	
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
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getDeath() {
		return death;
	}
	public void setDeath(String death) {
		this.death = death;
	}
	
	
	

}
