package com.groupay.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.groupay.api.dto.SmsWavyDTO;

@Service
public class WavyServices {
	
	@Value("${api.wavy.url}")
	private String wavyUrl;
	
	@Value("${api.wavy.key}")
	private String wavyKey;

	public void sendSms(String to, String message, String carrier) {
		
		if(to.trim() != "") {
			SmsWavyDTO dto = new SmsWavyDTO();
			dto.setCarrier(carrier);
			dto.setMessage(message);
			dto.setTo(to);
			
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Access-key", wavyKey);      
			headers.set("Content-Type", "application/json"); 
			HttpEntity<SmsWavyDTO> request = new HttpEntity<SmsWavyDTO>(dto, headers);
			restTemplate.postForObject(wavyUrl, request, SmsWavyDTO.class);
		}
	}
}
