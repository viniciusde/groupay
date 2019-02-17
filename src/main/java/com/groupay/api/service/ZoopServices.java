package com.groupay.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}
