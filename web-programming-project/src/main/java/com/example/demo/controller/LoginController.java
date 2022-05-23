package com.example.demo.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	@GetMapping("/login")
	public String login() {
		return "login";
	}
//	@GetMapping("/signup")
//	public String register() {
//		return "register";
//	}
//	@PostMapping("/signup-post")
//	public String registerPost(@RequestParam("username") String username, @RequestParam("password") String password) {
//		userService.createAccount(username, password);
//		return "redirect:/login";
//	}
	@GetMapping("/index")
	public String index() {
		return "employee/index";
	}
}
