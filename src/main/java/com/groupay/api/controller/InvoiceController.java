package com.groupay.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupay.api.model.Invoice;
import com.groupay.api.model.User;
import com.groupay.api.repository.InvoiceRepository;
import com.groupay.api.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value="Invoice Controller")
@CrossOrigin(origins="*")
public class InvoiceController {
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/invoices")
	@ApiOperation(value="Return all invoices")
	public List<Invoice> getInvoices() {
		List<Invoice> invoices = invoiceRepository.findAll();
		return invoices;
	}

	@PostMapping("/invoices")
	@ApiOperation(value="Create new invoice")
	public ResponseEntity<Invoice> newInvoice(@Valid @RequestBody Invoice invoice) {
		
		String cpf = invoice.getUserCPF();
		User user = userRepository.findByCpf(cpf);
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		invoice.setUserId(user.getId());
		invoiceRepository.save(invoice);
		return new ResponseEntity<>(invoice, HttpStatus.OK);
	}

	@PutMapping("/invoices/{id}")
	@ApiOperation(value="Update invoice")
	public ResponseEntity<Invoice> updateInvoice(@PathVariable("id") String id, @RequestBody Invoice invoice) {
		Optional<Invoice> invoiceData = invoiceRepository.findById(id);
		if(invoiceData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		invoice = invoiceRepository.save(invoiceData.get());
		
		return new ResponseEntity<>(invoice, HttpStatus.OK);
	}

	@DeleteMapping("/invoices/{id}")
	@ApiOperation(value="Remove invoice")
	public ResponseEntity<String> removeInvoice(@PathVariable("id") String id) {
		Optional<Invoice> invoiceData = invoiceRepository.findById(id);
		if(invoiceData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		invoiceRepository.save(invoiceData.get());

		return new ResponseEntity<>("Invoice was removed.", HttpStatus.OK);
	}

}
