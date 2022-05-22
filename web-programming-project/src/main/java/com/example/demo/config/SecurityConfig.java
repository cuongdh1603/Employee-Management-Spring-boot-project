package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.service.MyUserDetailService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private MyUserDetailService myUserDetailService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private CustomSuccessHandler customSuccessHandler;
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	//config static resources: css,js,... no need to be authenticated
	public void configure(WebSecurity web) {
		web.ignoring()
			.antMatchers("/css/*").antMatchers("/img/*").antMatchers("/js/*").antMatchers("/src/*");
	}
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailService).passwordEncoder(bCryptPasswordEncoder);
	}
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/signup").permitAll()
			.antMatchers("/signup-post").permitAll()
			.anyRequest().authenticated()
			
			.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/index")
			.successHandler(customSuccessHandler)
			.permitAll()
			.usernameParameter("username")
			.passwordParameter("password")
			.and()
			.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
			
			.and()
			//.csrf().disable()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login?logout").permitAll();
	}
}
