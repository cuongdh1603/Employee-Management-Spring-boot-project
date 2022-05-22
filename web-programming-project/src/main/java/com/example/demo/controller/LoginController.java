package com.example.demo.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	
//	@PostMapping("/login_acc")
//	public String showAccount(@ModelAttribute("account") Account account) {
//		System.out.println("Account: "+account.getUsername()+" "+account.getPassword());
//		return "admin/index";
//	}
	@GetMapping("/signup")
	public String register() {
		return "register";
	}
	@PostMapping("/signup-post")
	public String registerPost(@RequestParam("username") String username, @RequestParam("password") String password) {
		userService.createAccount(username, password);
		return "redirect:/login";
	}
	@GetMapping("/index")
	public String index() {
		return "employee/index";
	}
	@GetMapping("/return")
	public String returnHome() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
//		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		
		return "employee/index";
	}
}
