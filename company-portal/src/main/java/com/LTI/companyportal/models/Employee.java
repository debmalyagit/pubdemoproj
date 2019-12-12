package com.LTI.companyportal.models;

import javax.persistence.GeneratedValue;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Employee {
	
	@Id
	@GeneratedValue
	@JsonIgnore  public ObjectId _id;
	
	public String name;
	@JsonIgnore public String address;
	public String email;
	public String pan;
	
	@JsonIgnore public float annual_package;
	private float tds;
	
	public Employee() {};
	public Employee(/*ObjectId _id,*/ String name, String address, String email, String pan, float annual_package) {
		//this._id = _id;
		this.name = name;
		this.address = address;
		this.email = email;
		this.pan = pan;
		this.annual_package = annual_package;
		this.tds = (((annual_package/12) * 10)/100);
	}
	
	public ObjectId get_id() {
		return _id;
	}

	/*public void set_id(ObjectId _id) {
		this._id = _id;
	}*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public float getAnnual_package() {
		return annual_package;
	}

	public void setAnnual_package(float annual_package) {
		this.annual_package = annual_package;
	}
	public float getTds() {
		return tds;
	}
	public void setTds() {
		this.tds = (((this.annual_package/12) * 10)/100);
	} 
	
	 // Creating toString 
    @Override
    public String toString() 
    { 
        return "Employee [name="
            + name 
            + ", address="
            + address 
            + ", pan="
            + pan
            + ", annual_package="
            + annual_package
            + ", email="
            + email
            + ", tds="
            + tds
            + "]"; 
    }
	  
    

}
