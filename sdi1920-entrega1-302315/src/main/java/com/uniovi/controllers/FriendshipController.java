package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Friendship;
import com.uniovi.entities.User;
import com.uniovi.services.FriendshipService;
import com.uniovi.services.UsersService;

@Controller
public class FriendshipController {
	@Autowired
	private FriendshipService friendshipService;
	
	@Autowired
	private UsersService userService;

	@RequestMapping(value = "/invitation/send/{id}", method = RequestMethod.GET)
    public String send(Model model, @PathVariable Long id) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User userFrom = userService.getUserByEmail(email);
		Long userFromId = userFrom.getId();
		
		// Check invitation not repeated
		Friendship invitation = friendshipService.searchFriendshipByTwoUsers(userFromId, id);
		if(invitation != null) {
			model.addAttribute("message", "La petición de amistad para " + userService.getUser(id).getFullName() + " ya fue enviada.");
			return "redirect:/user/list";
		}
		
		Friendship friendInvitation = new Friendship(false);
		friendInvitation.setUserFrom(userService.getUser(userFromId));
		friendInvitation.setUserTo(userService.getUser(id));
		
		friendshipService.addFriendship(friendInvitation);
		
        return "redirect:/user/list";
    }
	
	// lista las solicitudes de amistad realizadas al usuario
	@RequestMapping(value = "/invitation/list", method = RequestMethod.GET)
	public String list(Model model, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User userFrom = userService.getUserByEmail(email);
		
		Page<Friendship> invitationList = friendshipService.searchFriendshipRequestedToUser(pageable, userFrom.getId());
		
		model.addAttribute("invitationList", invitationList.getContent());
		model.addAttribute("page", invitationList);
		
		return "invitation/list";
	}

}