package com.LTI.companyportal;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.LTI.companyportal.models.Employee;
import com.LTI.companyportal.models.JmsPublisherApplication;
import com.LTI.companyportal.repositories.EmployeeRepository;
import com.mongodb.client.MongoClients;


import org.springframework.data.mongodb.core.MongoOperations;

@RestController
@RequestMapping("/employee")


@SpringBootApplication
@ComponentScan
//@EnableAutoConfiguration
//@EnableMongoRepositories

public class CompanyPortalApplication {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	SimpleMongoClientDbFactory mongoDBFactory = new SimpleMongoClientDbFactory("mongodb://localhost:47017/rest_tutorial");
	Query query1 = new Query();
	MongoOperations mongoOps = new MongoTemplate(mongoDBFactory);
	
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
		query1.addCriteria(Criteria.where("name").is(name));
		Employee emp = mongoOps.findOne(query1, Employee.class);
		return "Employee Details: " + "Name: " + emp.getName() + " Address: " + emp.getAddress()
		+ " PAN: " + emp.getPan() + " Package: " + emp.getAnnual_package() + " E-Mail: " + emp.getEmail();
	}
	
	@RequestMapping(value="/pushtds/{name}", method = RequestMethod.GET)
	public void sendEmpDetails(@PathVariable("name") String name) {
		
		query1.addCriteria(Criteria.where("name").is(name));

		Employee emp = mongoOps.findOne(query1, Employee.class);
		System.out.println(emp.toString());
		JmsPublisherApplication jmsPub = new JmsPublisherApplication();
		jmsPub.sendMessage(emp);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CompanyPortalApplication.class, args);
	}

}
