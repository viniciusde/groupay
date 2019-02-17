package com.groupay.api.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "group")
public class Group {

	@Id
	private String id;

	private String name;

	private List<String> users;

	private User userOwner;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUserList(List<String> users) {
		this.users = users;
	}

	public User getUserOwner() {
		return userOwner;
	}

	public void setUserOwner(User userOwner) {
		this.userOwner = userOwner;
	}

}
