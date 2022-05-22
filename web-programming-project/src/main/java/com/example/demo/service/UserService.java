package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.model.Role;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.RoleRepository;

@Service
public class UserService {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private RoleRepository roleRepository;
	public Account createAccount(String username, String password) {
		if(!accountIsExist(username)) {
			Account account = new Account();
			account.setUsername(username);
			account.setPassword(bCryptPasswordEncoder.encode(password));
			
			Role role = roleRepository.findRoleById(2);
			account.setRole(role);
			
			accountRepository.save(account);
		}
		return null;
	}
	public boolean accountIsExist(String username) {
		Account account = accountRepository.findByUsername(username);
		if(account != null)
			return true;
		return false;
	}
	public Account findAccountByUsername(String username) {
		return accountRepository.findByUsername(username);
	}
	public List<Role> getRoleOfAccountByIdAccount(Integer accountId) {
		return roleRepository.getRoleOfAccountByIdAccount(accountId);
	}
}
