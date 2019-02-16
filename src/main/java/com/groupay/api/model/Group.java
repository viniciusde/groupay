package com.groupay.api.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "group")
public class Group {

	private String id;

	private String name;

	private List<User> userList;

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

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public User getUserOwner() {
		return userOwner;
	}

	public void setUserOwner(User userOwner) {
		this.userOwner = userOwner;
	}

}
