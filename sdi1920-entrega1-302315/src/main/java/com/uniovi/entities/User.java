package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class User {
	@Id
	@GeneratedValue
	private long id;

	@Column(unique = true)
	private String name;
	private String lastName;
	private String email;
	private String role;
	
	private String password;
	
	@Transient // no se almacena en la tabla
	private String passwordConfirm;
	
	@OneToMany(mappedBy="userFrom")
	private Set<Friendship> friendshipInvitations = new HashSet<Friendship>();
	@OneToMany(mappedBy="userTo")
	private Set<Friendship> friendshipRequests = new HashSet<Friendship>();

	public User(String name, String lastName, String email) {
		super();
		this.setEmail(email);
		this.name = name;
		this.lastName = lastName;
	}

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return this.name + " " + this.lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Friendship> getFriendshipInvitations() {
		return new HashSet<Friendship>(friendshipInvitations);
	}

	public void setFriendshipInvitations(Set<Friendship> friendshipInvitations) {
		this.friendshipInvitations = friendshipInvitations;
	}

	public Set<Friendship> getFriendshipRequests() {
		return new HashSet<Friendship>(friendshipRequests);
	}

	public void setFriendshipRequests(Set<Friendship> friendshipRequests) {
		this.friendshipRequests = friendshipRequests;
	}
	
	
}