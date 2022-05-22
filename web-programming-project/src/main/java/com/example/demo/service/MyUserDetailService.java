package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.model.Role;
@Service
public class MyUserDetailService implements UserDetailsService{
	@Autowired
	private UserService userService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = userService.findAccountByUsername(username);
		if(account != null) {
			List<Role> roles = userService.getRoleOfAccountByIdAccount(account.getId());
			Set<String> roleSet = new HashSet<>();
			for (Role r : roles) {
				roleSet.add("ROLE_"+r.getCode());
			}
			List<GrantedAuthority> authorities = roleSet.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
			return new User(account.getUsername(),account.getPassword(),authorities);
		}
		else throw new UsernameNotFoundException("Invalid username or password");
	}

}
