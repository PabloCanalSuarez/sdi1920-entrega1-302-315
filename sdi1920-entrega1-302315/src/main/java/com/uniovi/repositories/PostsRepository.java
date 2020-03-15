package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Post;

public interface PostsRepository extends CrudRepository<Post, Long>{

	@Query("SELECT p FROM Post p WHERE p.user.id = ?1")
	List<Post> findPostsById(Long id);
	
}
