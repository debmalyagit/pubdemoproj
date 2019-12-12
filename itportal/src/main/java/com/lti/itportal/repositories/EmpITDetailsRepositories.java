package com.lti.itportal.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lti.itportal.models.EmpITDetails;

public interface EmpITDetailsRepositories extends MongoRepository<EmpITDetails, String> {
	EmpITDetails findBy_id(String _id);
}
