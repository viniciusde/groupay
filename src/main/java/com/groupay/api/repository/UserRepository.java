package com.groupay.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.groupay.api.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByCpfAndPassword(String cpf, String password);
}