package com.groupay.api.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

	@Id
	private String id;

	private String zoopId;

	private String name;

	private String cpf;

	private String phone;

	private String email;

	private String password;

	private String balance;

	private List<Group> groups;
	private List<Invoice> invoices;
	private List<Invoice> groupInvoices;

	public List<Invoice> getGroupInvoices() {
		return groupInvoices;
	}

	public void setGroupInvoices(List<Invoice> groupInvoices) {
		this.groupInvoices = groupInvoices;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getZoopId() {
		return zoopId;
	}

	public void setZoopId(String zoopId) {
		this.zoopId = zoopId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

}
