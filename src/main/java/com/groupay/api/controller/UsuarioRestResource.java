package com.groupay.api.controller;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.groupay.api.model.Usuario;

@RepositoryRestResource(collectionResourceRel = "usuario", path = "usuario")
public interface UsuarioRestResource extends MongoRepository<Usuario, String> {

	List<Usuario> findByNome(@Param("nome") String name);

}