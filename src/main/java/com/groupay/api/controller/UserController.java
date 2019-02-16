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

import com.groupay.api.model.User;
import com.groupay.api.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserRepository userRepository;

	@GetMapping("/users")
	public List<User> getAllUsuarios() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@PostMapping("/users/create")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		userRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User user) {
		Optional<User> userData = userRepository.findById(id);
		if(userData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		user = userRepository.save(userData.get());
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<String> removeUser(@PathVariable("id") String id) {
		Optional<User> usuarioData = userRepository.findById(id);
		if(usuarioData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userRepository.save(usuarioData.get());

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
