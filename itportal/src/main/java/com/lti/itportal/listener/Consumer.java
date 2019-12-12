package com.lti.itportal.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.itportal.models.EmpITDetails;

import com.lti.itportal.repositories.EmpITDetailsRepositories;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	@Autowired
	private EmpITDetailsRepositories repository;
		
	@JmsListener(destination="standalone.queue")
	public void consumer(String message) {
		System.out.println("Consumer: Received a message: "+ message);
		ObjectMapper obj = new ObjectMapper();
		EmpITDetails empITD = new EmpITDetails();
		try {
			empITD = obj.readValue(message, EmpITDetails.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		empITD.setMonth();
		empITD.setYear();
		System.out.println("Consumer: "+ empITD.toString());
		try {
			repository.save(empITD);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Consumer: saved in MongoDB");
	}
	

}
