package com.uniovi.entities;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Post {
	
	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private User user;
	
	private Date date;
	private String title;
	private String contents;
	private boolean hasPicture;
	
	public Post(User user, Date date) {
		super();
		this.user = user;
		this.date = date;
	}
	
	public Post() {}

	public void setDate(Date now) {
		this.date = new Date(now.getTime());
	}
	
	public Date getDate() {
		return new Date(date.getTime());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public boolean isHasPicture() {
		return hasPicture;
	}

	public void setHasPicture(boolean hasPicture) {
		this.hasPicture = hasPicture;
	}
	
}
