package com.LTI.companyportal.models;
//import java.util.Scanner;

//import javax.jms.JMSConnectionFactory;
//import javax.jms.JMSDestinationDefinition;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

public class JmsPublisherApplication {
	 @Bean // Serialize message content to json using TextMessage
	  public MessageConverter jacksonJmsMessageConverter() {
	    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	    converter.setTargetType(MessageType.TEXT);
	    converter.setTypeIdPropertyName("_type");
	    return converter;
	  }

	
	@Autowired private JmsTemplate template;
	/*
	public static void main(String[] args) {
		SpringApplication.run(JmsPublisherApplication.class, args);
	}*/
	
	public void sendMessage (Employee employee) {		
		template.convertAndSend("empsample.queue",employee.toString());
		
	}

}
