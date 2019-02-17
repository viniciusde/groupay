package com.groupay.api.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.groupay.api.dto.CardRequestDTO;
import com.groupay.api.dto.CardResponseDTO;
import com.groupay.api.model.User;
import com.groupay.api.repository.UserRepository;
import com.groupay.api.service.ZoopServices;

@RestController
@RequestMapping("/api")
public class CardController {


	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ZoopServices zoopServices;

	@PostMapping("/cards/associate/{userId}")
	public ResponseEntity<Void> associateCard(@PathVariable("userId") String userId, @Valid @RequestBody CardRequestDTO card) {
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		zoopServices.createCard(card, user.get().getZoopId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
