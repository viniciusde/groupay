package com.groupay.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.groupay.api.dto.UserZoopDTO;
import com.groupay.api.model.User;
import com.groupay.api.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Value( "${api.zoop.auth}" )
	private String zoopAuth;
	
	@Value("${api.zoop.createUser.url}")
	private String zoopCreateUserUri;
	 
	@GetMapping("/users")
	public List<User> getAllUsuarios() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@PostMapping("/users/create")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", zoopAuth);      
		headers.set("Content-Type", "application/json"); 
		HttpEntity<UserZoopDTO> request = new HttpEntity<UserZoopDTO>(new UserZoopDTO(), headers);

		UserZoopDTO response = restTemplate.postForObject(zoopCreateUserUri, request, UserZoopDTO.class);
		user.setZoopId(response.getId());
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
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user) {
		User userLogin = userRepository.findByCpfAndPassword(user.getCpf(), new BCryptPasswordEncoder().encode(user.getPassword()));
		if(userLogin == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<User>(userLogin, HttpStatus.OK);
	}

}
