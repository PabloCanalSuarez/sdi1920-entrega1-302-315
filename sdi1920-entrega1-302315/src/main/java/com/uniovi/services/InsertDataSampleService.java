package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.User;

@Service
public class InsertDataSampleService {
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private FriendshipService friendshipService;
	
	@Autowired
	private RolesService rolesService;

	@PostConstruct
	public void init() {
		User user1 = new User("Pablo", "Admin", "admin@email.com");
		user1.setPassword("admin");
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

		User user6 = new User("Enrique", "Rolando", "enrique@email.com");
		user6.setPassword("123456");
		user6.setRole(rolesService.getRoles()[0]);
		
		User user7 = new User("owner", "owner", "o@email.com");
		user7.setPassword("123456");
		user7.setRole(rolesService.getRoles()[1]);

		User user8 = new User("Alberto", "Fernández", "alb@email.com");
		user8.setPassword("123456");
		user8.setRole(rolesService.getRoles()[0]);
		
		User user9 = new User("Jose Manuel", "Villanueva", "jose@email.com");
		user9.setPassword("123456");
		user9.setRole(rolesService.getRoles()[0]);
		
		User user10 = new User("Mariola", "Suárez", "mariola@email.com");
		user10.setPassword("123456");
		user10.setRole(rolesService.getRoles()[0]);
		
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		usersService.addUser(user7);
		usersService.addUser(user8);
		usersService.addUser(user9);
		usersService.addUser(user10);
		
		Friendship f1 = new Friendship(false);
		f1.setUserFrom(user2);
		f1.setUserTo(user3);
		
		Friendship f2 = new Friendship(false);
		f2.setUserFrom(user4);
		f2.setUserTo(user3);
		
		Friendship f3 = new Friendship(false);
		f3.setUserFrom(user5);
		f3.setUserTo(user3);
		
		Friendship f4 = new Friendship(false);
		f4.setUserFrom(user6);
		f4.setUserTo(user3);
		
		Friendship f5 = new Friendship(false);
		f5.setUserFrom(user9);
		f5.setUserTo(user3);
		
		Friendship f6 = new Friendship(false);
		f6.setUserFrom(user10);
		f6.setUserTo(user3);
		
		friendshipService.addFriendship(f1);
		friendshipService.addFriendship(f2);
		friendshipService.addFriendship(f3);
		friendshipService.addFriendship(f4);
		friendshipService.addFriendship(f5);
		friendshipService.addFriendship(f6);
	}
}