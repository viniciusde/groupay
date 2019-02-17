package com.groupay.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.groupay.api.model.Group;

public interface GroupRepository extends MongoRepository<Group, String> {
	
	List<Group> findByUsersIn(List<String> users);

}
