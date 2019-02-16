package com.groupay.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.groupay.api.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {


}