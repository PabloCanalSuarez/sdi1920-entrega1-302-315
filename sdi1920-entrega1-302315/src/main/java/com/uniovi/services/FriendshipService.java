package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friendship;
import com.uniovi.repositories.FriendshipRepository;

@Service
public class FriendshipService {
	
	@Autowired
	private FriendshipRepository friendshipRepository;
	
	public List<Friendship> getUsers() {
		List<Friendship> friendships = new ArrayList<Friendship>();
		friendshipRepository.findAll().forEach(friendships::add);
		return friendships;
	}

	public Friendship getFriendship(Long id) {
		return friendshipRepository.findById(id).get();
	}

	public void addFriendship(Friendship friendship) {
		friendshipRepository.save(friendship);
	}

	public void deleteFriendship(Long id) {
		friendshipRepository.deleteById(id);
	}
	
	public Friendship searchFriendshipByTwoUsers(Long userFromId, Long userToId) {
		return friendshipRepository.searchFriendshipByTwoUsers(userFromId, userToId);
	}
	
	public Page<Friendship> searchFriendshipSendByUser(Pageable pageable, Long userFromId) {
		return friendshipRepository.searchFriendshipSendByUser(pageable, userFromId);
	}
	
	public Page<Friendship> searchFriendshipRequestedToUser(Pageable pageable, Long userToId) {
		return friendshipRepository.searchFriendshipRequestedToUser(pageable, userToId);
	}
	
	public Page<Friendship> searchAllFriendshipByUser(Pageable pageable, Long userId) {
		return friendshipRepository.searchAllFriendshipByUser(pageable, userId);
	}

	public Page<Friendship> getInvitations(Pageable pageable) {
		return friendshipRepository.findAll(pageable);
	}
	
	public void acceptInvitation(Long id) {
		friendshipRepository.accept(id);
	}
}
