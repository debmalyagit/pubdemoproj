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

import com.LTI.companyportal.models.Employee;
import com.LTI.companyportal.repositories.EmployeeRepository;

@RestController
@RequestMapping("/employee")


@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@EnableMongoRepositories

public class CompanyPortalApplication {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public Employee getEmployeeById(@PathVariable("id") ObjectId id) {
		return employeeRepository.findBy_id(id);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void modifyEmployeeById(@PathVariable("id") ObjectId id, @Valid @RequestBody Employee employee){
		employee.set_id(ObjectId.get());
		employeeRepository.save(employee);
	}

	public static void main(String[] args) {
		SpringApplication.run(CompanyPortalApplication.class, args);
	}

}
