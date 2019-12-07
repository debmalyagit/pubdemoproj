package com.LTI.companyportal.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.LTI.companyportal.models.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
	Employee findBy_id(ObjectId _id);
}
