package com.lti.itportal.models;

//import com.lti.itportal.models.Employee;

import java.io.IOException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.itportal.repositories.EmpITDetailsRepositories;


public class EmpITDetails {
	
    private String _id;
    private String name;
    private String pan;
    private String email;
    private String month;
    private Integer year;
    private float tds;
    Calendar calendar = Calendar.getInstance();
    
    
    public EmpITDetails() {}; 
   
    public  EmpITDetails(String json){
    	ObjectMapper obj = new ObjectMapper();
    	try {
			EmpITDetails empITD1 = obj.readValue(json, EmpITDetails.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
        this._id = (pan + '|' + month + '|' + year.toString());
        
     
    }
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMonth() {		
		return month;
	}
	public void setMonth() {
		Calendar calendar = Calendar.getInstance();
    	switch( calendar.get(Calendar.MONTH)) {
    	case 0 : this.month = "January"; break;
    	case 1 : this.month = "February"; break;
    	case 2 : this.month = "March"; break;
    	case 3 : this.month = "April"; break;
    	case 4 : this.month = "May"; break;
    	case 5 : this.month = "June"; break;
    	case 6 : this.month = "July"; break;
    	case 7 : this.month = "August"; break;
    	case 8 : this.month = "September"; break;
    	case 9 : this.month = "October"; break;
    	case 10 : this.month = "November"; break;
    	case 11 : this.month = "December"; break;
    	default : this.month = null; break;
    	}
		
	}
	public Integer getYear() {
		return year;
	}
	public void setYear() {
		this.year = calendar.get(Calendar.YEAR);	
	}
	public float getTds() {
		return tds;
	}
	public void setTds(float tds) {
		this.tds = tds;
	} 
    
    @Override
    public String toString() 
    { 
        return "Employee [name="
            + name 
            + ", pan="
            + pan           
            + ", email="
            + email
            + ", tds="
            + tds
            + ", month="
            + month
            + ", year="
            + year.toString()
            + "]"; 
    }
	
 
}

