package com.groupay.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.groupay.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/api")
public class UsuarioController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@GetMapping("/usuarios")
	public List<Usuario> getAllUsuarios() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios;
	}

	@PostMapping("/usuarios/novo")
	public ResponseEntity<Usuario> novoUsuario(@Valid @RequestBody Usuario usuario) {
		usuarioRepository.save(usuario);
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}

	@PutMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> atualizaUsuario(@PathVariable("id") String id, @RequestBody Usuario usuario) {
		Optional<Usuario> usuarioData = usuarioRepository.findById(id);
		if(usuarioData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		usuario = usuarioRepository.save(usuarioData.get());
		
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}

	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<String> removeUsuario(@PathVariable("id") String id) {
		Optional<Usuario> usuarioData = usuarioRepository.findById(id);
		if(usuarioData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		usuarioRepository.save(usuarioData.get());

		return new ResponseEntity<>("Usuário foi removido", HttpStatus.OK);
	}

}
