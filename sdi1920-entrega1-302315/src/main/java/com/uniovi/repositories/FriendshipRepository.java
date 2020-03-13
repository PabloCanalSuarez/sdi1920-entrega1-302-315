package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Friendship;

public interface FriendshipRepository extends CrudRepository<Friendship, Long>{

	@Query("SELECT f FROM Friendship f WHERE f.userFrom.id = ?1 AND f.userTo.id = ?2")
	Page<Friendship> searchFriendshipByTwoUsers(Pageable pageable, Long userFromId, Long userToId);

	@Query("SELECT f FROM Friendship f WHERE f.userFrom.id = ?1")
	Page<Friendship> searchFriendshipSendByUser(Pageable pageable, Long userFromId);

	@Query("SELECT f FROM Friendship f WHERE f.userTo.id = ?1")
	Page<Friendship> searchFriendshipRequestedToUser(Pageable pageable, Long userToId);

	@Query("SELECT f FROM Friendship f WHERE f.userFrom.id = ?1 OR f.userTo.id = ?1")
	Page<Friendship> searchAllFriendshipByUser(Pageable pageable, Long userId);	
	
	Page<Friendship> findAll(Pageable pageable);
}
