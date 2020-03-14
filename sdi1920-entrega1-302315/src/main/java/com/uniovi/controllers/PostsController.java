package com.uniovi.controllers;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.services.PostsService;
import com.uniovi.services.UsersService;

@Controller
public class PostsController {
	@Autowired
	private PostsService postsService;
	
	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/post/add", method = RequestMethod.GET)
	public String getPost(Model model) {
		model.addAttribute("post", new Post());
		return "/post/add";
	}

	@RequestMapping(value = "/post/add", method = RequestMethod.POST)
	public String setPost(@Validated Post post) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User userFrom = usersService.getUserByEmail(email);
		
		post.setUser(userFrom);
		post.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		
		postsService.addPost(post);
		return "redirect:/post/list";
	}
	
	// lista las solicitudes de amistad realizadas al usuario
	@RequestMapping(value = "/post/list", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("postsList", postsService.getPosts());		
		return "post/list";
	}

}