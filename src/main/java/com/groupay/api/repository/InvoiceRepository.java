package com.groupay.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.groupay.api.model.Invoice;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {
	
	List<Invoice> findByUserId(String userId);
	
	List<Invoice> findByGroupId(String groupId);

}
