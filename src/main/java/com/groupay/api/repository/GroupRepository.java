package com.groupay.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.groupay.api.model.Group;

public interface GroupRepository extends MongoRepository<Group, String> {

}
