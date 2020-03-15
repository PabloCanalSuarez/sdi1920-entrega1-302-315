package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private RolesService roleService;

	@PostConstruct
	public void init() {
	}

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}

	public User getUser(Long id) {
		return usersRepository.findById(id).get();
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}

	public List<User> searchUsersByNameAndSurname(String searchText) {
		List<User> userList = new ArrayList<User>();
		searchText = "%" + searchText + "%";
		userList = usersRepository.searchByNameAndSurname(searchText);
		return userList;
	}

	public Page<User> searchUsersByNameSurnameAndMail(Pageable pageable, String searchText) {
		Page<User> users = new PageImpl<User>(new ArrayList<User>());

		searchText = "%" + searchText + "%";
		users = usersRepository.searchUsersByNameSurnameAndMail(pageable, searchText);

		return users;
	}

	public Page<User> getNotAdminUsersWithoutLoggedUser(Pageable pageable) {
		Page<User> users = new PageImpl<User>(new ArrayList<User>());

		String role = roleService.getRoles()[0];
		String emailOfUserToSkip = SecurityContextHolder.getContext().getAuthentication().getName();
		users = usersRepository.searchByRoleAndDontIncludeSpecificUser(pageable, role, emailOfUserToSkip);

		return users;
	}

	public Page<User> getUsersWithoutLoggedUser(Pageable pageable) {
		Page<User> users = new PageImpl<User>(new ArrayList<User>());

		String emailOfUserToSkip = SecurityContextHolder.getContext().getAuthentication().getName();
		users = usersRepository.searchDontIncludeSpecificUser(pageable, emailOfUserToSkip);

		return users;
	}

}