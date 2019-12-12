package com.lti.itportal;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import com.lti.itportal.repositories.EmpITDetailsRepositories;

@EnableJms
@SpringBootApplication
public class ITPortalApplication {
	 	 
	public static void main(String[] args) {
		SpringApplication.run(ITPortalApplication.class, args);		
	}
	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
			DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
			// This provides all boot's default to this factory, including the message converter
			configurer.configure(factory, connectionFactory);
			// You could still override some of Boot's default if necessary.
			return factory;
	}
	
	
}
