package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity	
public class Friendship {

	@Id
	@GeneratedValue
	private long id;
	
	private boolean accepted;
	
	@ManyToOne
	private User userFrom;
	@ManyToOne
	private User userTo;
	
	public Friendship(boolean accepted) {
		super();
		this.accepted = accepted;
	}

	public Friendship() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public User getUserFrom() {
		return userFrom;
	}

	public void setUserFrom(User userFrom) {
		this.userFrom = userFrom;
	}

	public User getUserTo() {
		return userTo;
	}

	public void setUserTo(User userTo) {
		this.userTo = userTo;
	}
}
