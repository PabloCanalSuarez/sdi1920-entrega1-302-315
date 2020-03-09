package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;

@Service
public class InsertDataSampleService {
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private RolesService rolesService;

	@PostConstruct
	public void init() {
		User user1 = new User("Pablo", "Cañal", "admin@email.com");
		user1.setPassword("123456");
		user1.setRole(rolesService.getRoles()[1]);
		
		usersService.addUser(user1);
	}
}