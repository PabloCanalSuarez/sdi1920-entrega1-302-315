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
		User user1 = new User("Admin", "Admin", "admin@email.com");
		user1.setPassword("123456");
		user1.setRole(rolesService.getRoles()[1]);
		
		User user2 = new User("Jesús", "Quesada", "jesus@email.com");
		user2.setPassword("123456");
		user2.setRole(rolesService.getRoles()[0]);

		User user3 = new User("Clara", "Raposo", "clara@email.com");
		user3.setPassword("123456");
		user3.setRole(rolesService.getRoles()[0]);
		
		User user4 = new User("Diego", "Ramírez", "diego@email.com");
		user4.setPassword("123456");
		user4.setRole(rolesService.getRoles()[0]);

		User user5 = new User("Edward", "de la Cal", "edward@email.com");
		user5.setPassword("123456");
		user5.setRole(rolesService.getRoles()[0]);

		
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
	}
}