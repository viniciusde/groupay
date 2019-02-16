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
import org.springframework.web.client.RestTemplate;

import com.groupay.api.model.Invoice;
import com.groupay.api.repository.InvoiceRepository;

@RestController
@RequestMapping("/api")
public class InvoiceController {
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@GetMapping("/invoices")
	public List<Invoice> getInvoices() {
		List<Invoice> invoices = invoiceRepository.findAll();
		return invoices;
	}

	@PostMapping("/invoices")
	public ResponseEntity<Invoice> newInvoice(@Valid @RequestBody Invoice invoice) {
		
		//RestTemplate restTemplate = new RestTemplate();
        //restTemplate.postForEntity("", invoice, Invoice.class);;
        
		invoiceRepository.save(invoice);
		return new ResponseEntity<>(invoice, HttpStatus.OK);
	}

	@PutMapping("/invoices/{id}")
	public ResponseEntity<Invoice> updateInvoice(@PathVariable("id") String id, @RequestBody Invoice invoice) {
		Optional<Invoice> invoiceData = invoiceRepository.findById(id);
		if(invoiceData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		invoice = invoiceRepository.save(invoiceData.get());
		
		return new ResponseEntity<>(invoice, HttpStatus.OK);
	}

	@DeleteMapping("/invoices/{id}")
	public ResponseEntity<String> removeInvoice(@PathVariable("id") String id) {
		Optional<Invoice> invoiceData = invoiceRepository.findById(id);
		if(invoiceData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		invoiceRepository.save(invoiceData.get());

		return new ResponseEntity<>("Invoice was removed.", HttpStatus.OK);
	}

}
