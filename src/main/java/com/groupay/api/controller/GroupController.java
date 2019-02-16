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

import com.groupay.api.model.Group;
import com.groupay.api.repository.GroupRepository;

@RestController
@RequestMapping("/api")
public class GroupController {
	
	@Autowired
	GroupRepository groupRepository;
	
	@GetMapping("/groups")
	public List<Group> getGroups() {
		List<Group> groups = groupRepository.findAll();
		return groups;
	}

	@PostMapping("/groups")
	public ResponseEntity<Group> createGroup(@Valid @RequestBody Group group) {
		
		groupRepository.save(group);
		return new ResponseEntity<>(group, HttpStatus.OK);
	}

	@PutMapping("/groups/{id}")
	public ResponseEntity<Group> updateGroup(@PathVariable("id") String id, @RequestBody Group group) {
		Optional<Group> groupData = groupRepository.findById(id);
		if(groupData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		group = groupRepository.save(groupData.get());
		
		return new ResponseEntity<>(group, HttpStatus.OK);
	}

	@DeleteMapping("/groups/{id}")
	public ResponseEntity<String> removeGroup(@PathVariable("id") String id) {
		Optional<Group> groupData = groupRepository.findById(id);
		if(groupData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		groupRepository.save(groupData.get());

		return new ResponseEntity<>("Group was removed.", HttpStatus.OK);
	}

}
