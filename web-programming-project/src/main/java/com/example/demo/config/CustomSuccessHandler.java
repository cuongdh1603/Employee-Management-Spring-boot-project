package com.example.demo.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String redirectUrl = null;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
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
		if(redirectUrl == null) {
			throw new IllegalStateException();
		}
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
	}

}
