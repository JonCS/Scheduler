package com.jw.meetingscheduler.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jw.meetingscheduler.dto.LoginDto;
import com.jw.meetingscheduler.model.User;
import com.jw.meetingscheduler.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody LoginDto login) throws ServletException {
		//check that username and password are both filled out
		if(login.getUsername() == null || login.getPassword() == null)
			throw new ServletException("Please fill in username and password");
		
		String username = login.getUsername();
		
		//check passwords
		User user = userService.getUserByUsername(username);
		
		if(!passwordEncoder.matches(login.getPassword(), user.getPassword()))
			throw new ServletException("Password is incorrect");
		
		//get creation and expiration date
		Calendar calendar = Calendar.getInstance();
		Date issueTime = calendar.getTime();
		
		calendar.add(Calendar.MINUTE, 10);
		Date expTime = calendar.getTime();
		
		String jwtToken = Jwts.builder()
				.claim("roles", "user")
				.claim("username", username)
				.claim("congId", user.getCongregation().getId())
				.claim("congName", user.getCongregation().getName())
				.setIssuedAt(issueTime)
				.setExpiration(expTime)
	            .signWith(SignatureAlgorithm.HS256, "secretkey")
	            .compact();

		return jwtToken;
	}
	
	@RequestMapping(value = "/secure/test", method = RequestMethod.GET)
	public String testSecure() {
		return "You rock!";
	}

}
