package com.uniovi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/" )
	public String index() {
		return "index";
	}
	
	@RequestMapping("/secret" )
	public String secret() {
		return "secret";
	}
	
	@RequestMapping("/accessDenied" )
	public String accessDenied() {
		return "accessDenied.html";
	}
}
