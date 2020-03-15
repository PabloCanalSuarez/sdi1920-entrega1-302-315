package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendshipRepository;

@Service
public class FriendshipService {

	@Autowired
	private FriendshipRepository friendshipRepository;

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

	public Page<Friendship> searchFriendshipReceivedByUser(Pageable pageable, Long userToId) {
		return friendshipRepository.searchFriendshipReceivedByUser(pageable, userToId);
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

	public Page<Friendship> searchFriendsOfUser(Pageable pageable, Long id) {
		return friendshipRepository.searchFriendsOfUser(pageable, id);
	}

	public List<User> getUsersToByUserFrom(Long idUserFrom) {
		return friendshipRepository.getUsersToByUserFrom(idUserFrom);
	}
}
