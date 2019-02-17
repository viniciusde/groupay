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

import com.groupay.api.model.Group;
import com.groupay.api.repository.GroupRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@Api(value="Group Controller")
@CrossOrigin(origins="*")
public class GroupController {
	
	@Autowired
	GroupRepository groupRepository;
	
	@GetMapping("/groups")
	@ApiOperation(value="Return all groups")
	public List<Group> getGroups() {
		List<Group> groups = groupRepository.findAll();
		return groups;
	}
	
	@GetMapping("/groups/{id}")
	@ApiOperation(value="Return Group")
	public ResponseEntity<Group> getGroup(@PathVariable("id") String id) {
		Optional<Group> groupData = groupRepository.findById(id);
		if(!groupData.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(groupData.get(), HttpStatus.OK);
	}

	@PostMapping("/groups")
	@ApiOperation(value="Create new Group")
	public ResponseEntity<Group> createGroup(@Valid @RequestBody Group group) {
		
		groupRepository.save(group);
		return new ResponseEntity<>(group, HttpStatus.OK);
	}

	@PutMapping("/groups/{id}")
	@ApiOperation(value="Update Group")
	public ResponseEntity<Group> updateGroup(@PathVariable("id") String id, @RequestBody Group group) {
		Optional<Group> groupData = groupRepository.findById(id);
		if(groupData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		group = groupRepository.save(group);
		
		return new ResponseEntity<>(group, HttpStatus.OK);
	}

	@DeleteMapping("/groups/{id}")
	@ApiOperation(value="Remove Group")
	public ResponseEntity<String> removeGroup(@PathVariable("id") String id) {
		Optional<Group> groupData = groupRepository.findById(id);
		if(groupData == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		groupRepository.save(groupData.get());

		return new ResponseEntity<>("Group was removed.", HttpStatus.OK);
	}

}
