package com.groupay.api.controller;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

import com.groupay.api.dto.TransactionRequestDTO;
import com.groupay.api.dto.UserZoopDTO;
import com.groupay.api.model.Invoice;
import com.groupay.api.model.User;
import com.groupay.api.repository.InvoiceRepository;
import com.groupay.api.repository.UserRepository;
import com.groupay.api.service.WavyServices;
import com.groupay.api.service.ZoopServices;

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
	WavyServices wavyService;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ZoopServices zoopServices;

	
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
		
		try {
		StringBuilder messageSms = new StringBuilder();
		messageSms.append("Você recebeu uma nova cobrança:\n");
		messageSms.append(invoice.getName() + " - " + invoice.getValue() + "\n");
		DateTimeFormatter formatador =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = invoice.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		messageSms.append("Venc.:" + date.format(formatador));

		wavyService.sendSms(user.getPhone(), messageSms.toString(), "VIVO");
		}catch(Exception e) {}
		
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
	
	@PostMapping("/invoices/{invoiceId}/user/{userId}/payment")
	@ApiOperation(value="Pay invoice")
	public ResponseEntity<Invoice> payInvoice(@PathVariable("invoiceId") String invoiceId,
			@PathVariable("userId") String userId) {
		Optional<Invoice> invoiceData = invoiceRepository.findById(invoiceId);
		if(!invoiceData.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Optional<User> userData = userRepository.findById(userId);
		if(!userData.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		User user = userData.get();
		
		UserZoopDTO zoopUser = zoopServices.getUserById(user.getZoopId());
		if(zoopUser == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Invoice invoice = invoiceData.get();
		
		double userBalance = Double.parseDouble(zoopUser.getCurrentBalance());
		//Hardcoded seller as we can't create new sellers
		String hardcodedSellerId = "05f44463d78f449bbf526c6ca5e7ff7f";
		DecimalFormat df = new DecimalFormat("#.00"); 
		String formattedValue = df.format(invoice.getValue()).replace(".", "").replace(",", "");
		
		if(userBalance >= invoice.getValue()) {
			zoopServices.transferP2P(formattedValue, user.getZoopId(), hardcodedSellerId);
		} else {
			TransactionRequestDTO transactionRequest = new TransactionRequestDTO();

			transactionRequest.setAmount(Double.parseDouble(formattedValue));
			transactionRequest.setCurrency("BRL");
			transactionRequest.setCustomer(user.getZoopId());
			transactionRequest.setDescription("venda");
			transactionRequest.setPaymentType("credit");
			transactionRequest.setSellerId(hardcodedSellerId);
		
			zoopServices.createTransaction(transactionRequest);		
		}
				
		invoice.setPaid(true);
		invoiceRepository.save(invoice);
		
		return new ResponseEntity<>(HttpStatus.OK);
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
