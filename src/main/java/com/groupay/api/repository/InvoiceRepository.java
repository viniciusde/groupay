package com.groupay.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.groupay.api.model.Invoice;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {

}
