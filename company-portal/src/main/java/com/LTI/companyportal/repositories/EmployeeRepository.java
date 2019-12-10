package com.LTI.companyportal.repositories;

import org.bson.types.ObjectId;
//import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Component;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.MongoOperations;

import com.LTI.companyportal.models.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
	Employee findBy_id(ObjectId _id);
}
