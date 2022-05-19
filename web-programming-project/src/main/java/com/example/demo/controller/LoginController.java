package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Account;

@Controller
@RequestMapping
public class LoginController {
	@GetMapping("/login")
	public String login(Model model) {
		Account account = new Account();
		model.addAttribute("account",account);
		return "login";
	}
//	@GetMapping("/admin")
//	public String admin() {
//		return "admin/index";
//	}
//	@GetMapping("/manager")
//	public String manager() {
//		return "manager/index";
//	}
//	@GetMapping("/employee")
//	public String employee() {
//		return "employee/index";
//	}
	@PostMapping("/login_acc")
	public String showAccount(@ModelAttribute("account") Account account) {
		System.out.println("Account: "+account.getUsername()+" "+account.getPassword());
		return "admin/index";
	}
}
