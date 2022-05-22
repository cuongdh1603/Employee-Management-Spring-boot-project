package com.example.demo.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
@Log4j2
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		String redirectUrl = null;
		for(GrantedAuthority grantedAuthority : authorities) {
			log.info(grantedAuthority.getAuthority());
			if(grantedAuthority.getAuthority().equals("ROLE_AD")) {
				redirectUrl = "/admin";
				break;
			}
			else if(grantedAuthority.getAuthority().equals("ROLE_MA")) {
				redirectUrl = "/manager";
				break;
			}
			else if(grantedAuthority.getAuthority().equals("ROLE_EM")){
				redirectUrl = "/employ";
				break;
			}
		}
		log.info("redirect URL: "+redirectUrl);
		response.sendRedirect(redirectUrl +"/accessDenied");
	}

}
