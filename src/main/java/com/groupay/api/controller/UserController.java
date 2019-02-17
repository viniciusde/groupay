package com.groupay.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupay.api.dto.UserZoopDTO;
import com.groupay.api.model.User;
import com.groupay.api.repository.UserRepository;
import com.groupay.api.service.ZoopServices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value="User Controller")
@CrossOrigin(origins="*")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ZoopServices zoopServices;
	 
	@GetMapping("/users")
	@ApiOperation(value="Return all Users")
	public List<User> getAllUsuarios() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@GetMapping("/users/{id}")
	@ApiOperation(value="Return User")
	public ResponseEntity<User> getUser(@PathVariable("id") String id) {
		Optional<User> usuarioData = userRepository.findById(id);
		if(!usuarioData.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		User user = usuarioData.get();
		
		UserZoopDTO zoopUser = zoopServices.getUserById(user.getZoopId());
		user.setBalance(zoopUser.getCurrentBalance());
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PostMapping("/users/create")
	@ApiOperation(value="Create new User")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		UserZoopDTO zoopUser = zoopServices.createZoopUser(user);
		user.setZoopId(zoopUser.getId());
		userRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping("/users/{id}")
	@ApiOperation(value="Update User")
	public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User user) {
		Optional<User> userData = userRepository.findById(id);
		if(userData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		user = userRepository.save(userData.get());
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
	@ApiOperation(value="Remove User")
	public ResponseEntity<String> removeUser(@PathVariable("id") String id) {
		Optional<User> usuarioData = userRepository.findById(id);
		if(usuarioData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userRepository.save(usuarioData.get());

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/login")
	@ApiOperation(value="Login")
	public ResponseEntity<User> login(@RequestBody User user) {
		User userLogin = userRepository.findByCpfAndPassword(user.getCpf(), new BCryptPasswordEncoder().encode(user.getPassword()));
		if(userLogin == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<User>(userLogin, HttpStatus.OK);
	}

}
