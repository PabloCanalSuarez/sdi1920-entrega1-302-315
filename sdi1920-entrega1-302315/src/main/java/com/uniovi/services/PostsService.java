package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Post;
import com.uniovi.repositories.PostsRepository;

@Service
public class PostsService {

	@Autowired
	private PostsRepository postsRepository;

	public List<Post> getPosts() {
		List<Post> posts = new ArrayList<Post>();
		postsRepository.findAll().forEach(posts::add);
		return posts;
	}
	
	public void addPost(Post post) {
		postsRepository.save(post);
	}
	
	public Post getPost(Long id) {
		return postsRepository.findById(id).get();
	}

	public List<Post> getPostsByUserId(Long id) {
		return postsRepository.findPostsById(id);
	}
}
