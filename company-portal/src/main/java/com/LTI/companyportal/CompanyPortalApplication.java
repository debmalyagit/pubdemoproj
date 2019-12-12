package com.LTI.companyportal;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.jms.Queue;
import javax.validation.Valid;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper; 

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoOperations;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.LTI.companyportal.models.Employee;
import com.LTI.companyportal.repositories.EmployeeRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/employee")
@SpringBootApplication
@ComponentScan
public class CompanyPortalApplication {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Autowired
	Queue queue;
	
	//@Value("${data.mongodb.uri}")
	//private String mongoURI;
	SimpleMongoClientDbFactory mongoDBFactory = new SimpleMongoClientDbFactory("mongodb://localhost:47017/rest_tutorial");
	
	//SimpleMongoClientDbFactory mongoDBFactory = new SimpleMongoClientDbFactory(mongoURI);
	
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public Employee getEmployeeById(@PathVariable("id") ObjectId id) {
		return employeeRepository.findBy_id(id);
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.PUT)
	public void modifyEmployeeById(@Valid @RequestBody Employee employee){
		//employee.set_id(ObjectId.get());		
		employeeRepository.save(employee);
	}
	
	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	public String getEmployeeByName(@PathVariable("name") String name){
		Query query1 = new Query();
		query1.addCriteria(Criteria.where("name").is(name));
		
		MongoOperations mongoOps = new MongoTemplate(mongoDBFactory);
		Employee emp = mongoOps.findOne(query1, Employee.class);
		return "Employee Details: " + "Name: " + emp.getName() + " Address: " + emp.getAddress()
		+ " PAN: " + emp.getPan() + " Package: " + emp.getAnnual_package() + " E-Mail: " + emp.getEmail();
	}
	@JsonIgnoreProperties(ignoreUnknown = true)
	@RequestMapping(value="/pushtds/{name}", method = RequestMethod.GET)
	public String sendEmpDetails(@PathVariable("name") String name) {
		String jsonStr=null;
		Query query2 = new Query();
		query2.addCriteria(Criteria.where("name").is(name));
		MongoOperations mongoOps = new MongoTemplate(mongoDBFactory);
		Employee emp = mongoOps.findOne(query2, Employee.class);
		System.out.println(emp.toString());
		
		ObjectMapper Obj = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
			//Obj.setDateFormat(df);
			
			jsonStr = Obj.writeValueAsString(emp);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jmsTemplate.convertAndSend(queue,jsonStr);
		return "Published the Message" + jsonStr ;
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CompanyPortalApplication.class, args);
	}

}
