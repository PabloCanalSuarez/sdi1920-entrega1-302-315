package com.uniovi.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.FriendshipService;
import com.uniovi.services.PostsService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private PostsService postsService;

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private SignUpFormValidator signUpFormValidator;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private FriendshipService friendshipService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your email or password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}
	
	@RequestMapping("/user/list")
	public String getListado(Model model, Pageable pageable, @RequestParam(value="", required=false)String searchText) {
		Page<User> users = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		
		if(activeUser.getRole().equals(rolesService.getRoles()[0])) {
			if(searchText != null && !searchText.isEmpty()) {
				users = usersService.searchUsersByNameSurnameAndMail(pageable, searchText.toLowerCase());
			} else {
				if (searchText == null) searchText = "";
				users = usersService.getNotAdminUsersWithoutLoggedUser(pageable);
			}
		} else {
			if(searchText != null && !searchText.isEmpty()) {
				users = usersService.searchUsersByNameSurnameAndMail(pageable, searchText.toLowerCase());
			} else {
				if (searchText == null) searchText = "";
				users = usersService.getUsersWithoutLoggedUser(pageable);
			}
		}
		 
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		model.addAttribute("searchText", searchText);
		model.addAttribute("invitationList", friendshipService.getUsersToByUserFrom(activeUser.getId()));
		return "user/list";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if (result.hasErrors())
			return "signup";
		
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:home";
	}
	
	@RequestMapping(value = "/user/delete", method = RequestMethod.POST)
	public String getListado(Model model, @RequestParam Map<String,String> requestParams) {
		for (String s : requestParams.keySet()) {
			try {
				Long id = Long.decode(s);
				
				postsService.getPostsByUserId(id).forEach( p -> postsService.deletePost(p.getId()) );
				friendshipService.deleteAllInfoOfUser(id);
				usersService.deleteUser(id);
			} catch(NumberFormatException e) {
				// Wrong param
			}
		}
		
		return "redirect:/user/list";
	}
}
