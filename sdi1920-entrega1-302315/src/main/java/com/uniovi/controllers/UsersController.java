package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {
	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private SignUpFormValidator signUpFormValidator;
	
	@Autowired
	private RolesService rolesService;

	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		return "home";
	}
	
	@RequestMapping("/user/list")
	public String getListado(Model model, @RequestParam(value="", required=false)String searchText) {
		
		if(searchText != null && !searchText.isEmpty()) {
			model.addAttribute("usersList", usersService.searchUsersByNameAndSurname(searchText));
		} else {
			model.addAttribute("usersList", usersService.getUsers());
		}		
		
		return "user/list";
	}

}
