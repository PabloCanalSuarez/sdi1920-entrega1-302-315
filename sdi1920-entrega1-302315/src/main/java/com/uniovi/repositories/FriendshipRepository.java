package com.uniovi.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Friendship;

public interface FriendshipRepository extends CrudRepository<Friendship, Long>{

	@Query("SELECT f FROM Friendship f WHERE f.userFrom.id = ?1 AND f.userTo.id = ?2")
	Friendship searchFriendshipByTwoUsers(Long userFromId, Long userToId);

	@Query("SELECT f FROM Friendship f WHERE f.userFrom.id = ?1")
	Page<Friendship> searchFriendshipSendByUser(Pageable pageable, Long userFromId);
	
	@Query("SELECT f FROM Friendship f WHERE f.userTo.id = ?1")
	Page<Friendship> searchFriendshipReceivedByUser(Pageable pageable, Long userToId);

	@Query("SELECT f FROM Friendship f WHERE f.userTo.id = ?1 AND f.accepted = 'F'")
	Page<Friendship> searchFriendshipRequestedToUser(Pageable pageable, Long userToId);

	@Query("SELECT f FROM Friendship f WHERE f.userFrom.id = ?1 OR f.userTo.id = ?1")
	Page<Friendship> searchAllFriendshipByUser(Pageable pageable, Long userId);	
	
	@Query("SELECT f FROM Friendship f WHERE f.userFrom.id = ?1 AND f.accepted = 'T'")
	Page<Friendship> searchFriendsOfUser(Pageable pageable, Long id);
	
	@Query("SELECT f FROM Friendship f WHERE f.userFrom.id = ?1 AND f.accepted = 'T'")
	List<Friendship> searchFriendsOfUser(Long id);
	
	Page<Friendship> findAll(Pageable pageable);
	
	@Modifying
	@Transactional
	@Query("UPDATE Friendship f SET f.accepted = 'T' WHERE f.id = ?1")
	void accept(Long id);
}
