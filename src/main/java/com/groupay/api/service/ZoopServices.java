package com.groupay.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.groupay.api.dto.CardRequestDTO;
import com.groupay.api.dto.CardResponseDTO;
import com.groupay.api.dto.UserZoopDTO;
import com.groupay.api.model.User;

@Service
public class ZoopServices {
	
	@Value( "${api.zoop.auth}" )
	private String zoopAuth;
	
	@Value("${api.zoop.createUser.url}")
	private String zoopCreateUserUrl;
	
	@Value("${api.zoop.findUser.url}")
	private String zoopFindUserUrl;
	
	@Value("${api.zoop.token.card.url}")
	private String zoopTokenCardUrl;
	
	@Value("${api.zoop.cards.url}")
	private String zoopCardsUrl;
	
	public UserZoopDTO getUserById(String zoopId) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", zoopAuth);      
		headers.set("Content-Type", "application/json"); 
		
		String zoopFindUserUrlReplaced = zoopFindUserUrl.replace("{zoopId}", zoopId);
		HttpEntity request = new HttpEntity(headers);
		
		ResponseEntity<UserZoopDTO> exchange = restTemplate.exchange(zoopFindUserUrlReplaced, HttpMethod.GET, request, UserZoopDTO.class);
		return exchange.getBody();
	}

	public UserZoopDTO createZoopUser(User user) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", zoopAuth);      
		headers.set("Content-Type", "application/json"); 
		HttpEntity<UserZoopDTO> request = new HttpEntity<UserZoopDTO>(new UserZoopDTO(), headers);

		return restTemplate.postForObject(zoopCreateUserUrl, request, UserZoopDTO.class);
	}
	
	public ResponseEntity<CardResponseDTO> createCard(CardRequestDTO card, String zoopId) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", zoopAuth);
		headers.set("Content-Type", "application/json");
		HttpEntity<CardRequestDTO> request = new HttpEntity<CardRequestDTO>(card, headers);

		CardResponseDTO response = restTemplate.postForObject(zoopTokenCardUrl, request, CardResponseDTO.class);
		this.associateCard(zoopId, response.getId());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public void associateCard(String zoopId, String token) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", zoopAuth);
		headers.set("Content-Type", "application/json");
		CardRequestDTO dto = new CardRequestDTO();
		dto.setCustomer(zoopId);
		dto.setToken(token);
		HttpEntity<CardRequestDTO> request = new HttpEntity<CardRequestDTO>(dto, headers);
		
		restTemplate.postForObject(zoopCardsUrl, request, CardResponseDTO.class);
	}
}
