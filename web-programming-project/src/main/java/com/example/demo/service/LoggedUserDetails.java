package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.Account;
import com.example.demo.model.Employee;
import com.example.demo.model.Role;

public class LoggedUserDetails implements UserDetails{
	private Account account;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Set<Role> roles = new HashSet<Role>();
		roles.add(account.getRole());
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for(Role r : roles) {
			authorities.add(new SimpleGrantedAuthority(r.getCode()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return account.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	public String getFullName() {
		return "Ok 12345";
	}
}
