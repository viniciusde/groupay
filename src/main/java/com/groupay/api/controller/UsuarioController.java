package com.groupay.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupay.api.model.Usuario;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value="API Rest Usuários")
public class UsuarioController {
	
	@GetMapping("/usuarios")
	@ApiOperation(value="Retorna todos os usuários")
	public List<Usuario> getAllUsuarios() {
		List<Usuario> usuarios = new ArrayList<>();
		return usuarios;
	}

	@PostMapping("/usuarios/novo")
	@ApiOperation(value="Cria um novo usuário")
	public ResponseEntity<Usuario> novoUsuario(@Valid @RequestBody Usuario usuario) {
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}

	@PutMapping("/usuarios/{id}")
	@ApiOperation(value="Atualiza um usuário")
	public ResponseEntity<Usuario> atualizaUsuario(@PathVariable("id") int id, @RequestBody Usuario usuario) {
		
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}

	@DeleteMapping("/usuarios{id}")
	@ApiOperation(value="Remove um usuário")
	public ResponseEntity<String> removeUsuario(@PathVariable("id") int id) {


		return new ResponseEntity<>("Usuário foi removido", HttpStatus.OK);
	}

}
