package com.uniovi.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.uniovi.entities.Post;
import com.uniovi.entities.User;
import com.uniovi.services.FriendshipService;
import com.uniovi.services.PostsService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.PostFormValidator;

@Controller
public class PostsController {
	@Autowired
	private PostsService postsService;
	
	@Autowired
	private FriendshipService friendshipService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private PostFormValidator postValidator;
	
	@RequestMapping(value = "/post/add", method = RequestMethod.GET)
	public String getPost(Model model) {
		model.addAttribute("post", new Post());
		return "/post/add";
	}

	@RequestMapping(value = "/post/add", method = RequestMethod.POST)
	public String setPost(@Validated Post post, BindingResult result, @RequestParam("picture") MultipartFile picture) {
		
		postValidator.validate(post, result);
		if (result.hasErrors())
			return "post/add";
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User userFrom = usersService.getUserByEmail(email);
		
		post.setUser(userFrom);
		post.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		
		if (!picture.isEmpty()) {
			processPic(picture, ""+post.getId());
			post.setHasPicture(true);
		}

		postsService.addPost(post);
		
		return "redirect:/post/list";
	}
	
	private void processPic(MultipartFile picture, String fileName) {
		try {
			InputStream is = picture.getInputStream();
			Files.copy(is, Paths.get("src/main/resources/static/pictures/" + fileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(value = "/post/list", method = RequestMethod.GET)
	public String list(Model model) {		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User userFrom = usersService.getUserByEmail(email);
		
		model.addAttribute("postsList", postsService.getPostsByUserId(userFrom.getId()));		
		return "post/list";
    }

	@RequestMapping(value = "/post/friends/{id}", method = RequestMethod.GET)
	public String list(Model model, @PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = usersService.getUserByEmail(email);
		
		// Check if id is friend
		Object[] friends = friendshipService.searchFriendsOfUser( user.getId() ).stream().filter( f -> f.getUserTo().getId() == id ).toArray();
		if (friends.length == 0) {
			throw new org.springframework.security.access.AccessDeniedException("403 returned");
		}
		
		// Get posts
		List<Post> posts = postsService.getPostsByUserId(id);
		
		model.addAttribute("postsList", posts);
		return "post/friends";
	}
	
	

}
