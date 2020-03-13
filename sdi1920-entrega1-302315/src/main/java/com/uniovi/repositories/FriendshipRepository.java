package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Friendship;

public interface FriendshipRepository extends CrudRepository<Friendship, Long>{
	
//	@Query("SELECT u FROM User u WHERE (LOWER(u.name) LIKE LOWER(?1) OR LOWER(u.lastName) LIKE LOWER(?1))")
//	List<User> searchByNameAndSurname(String searchText);
	
}
