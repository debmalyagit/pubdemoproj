package com.LTI.companyportal;


import javax.jms.Queue;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

import com.LTI.companyportal.models.Employee;
import com.LTI.companyportal.repositories.EmployeeRepository;

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
	
	@RequestMapping(value="/pushtds/{name}", method = RequestMethod.GET)
	public String sendEmpDetails(@PathVariable("name") String name) {
		Query query2 = new Query();
		query2.addCriteria(Criteria.where("name").is(name));
		MongoOperations mongoOps = new MongoTemplate(mongoDBFactory);
		Employee emp = mongoOps.findOne(query2, Employee.class);
		System.out.println(emp.toString());
		
		//jmsTemplate.convertAndSend((Destination) queue, emp.toString());
		
		//jmsTemplate.convertAndSend(queue,emp);
		return "Published the Message" + "{Name:" + emp.getName() + ",Address:" + emp.getAddress()
		+ ",PAN: " + emp.getPan() + ",Package: " + emp.getAnnual_package() + ",E-Mail: " + emp.getEmail() +"}" ;
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CompanyPortalApplication.class, args);
	}

}
