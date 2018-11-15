package com.jw.meetingscheduler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jw.meetingscheduler.exception.UserDoesNotExistException;
import com.jw.meetingscheduler.model.User;
import com.jw.meetingscheduler.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/api/users", method = RequestMethod.GET)
	public List<User> getUsers(){
		return userService.getUsers();
	}
	
	@RequestMapping(value = "/api/user/username/{username}", method = RequestMethod.GET)
	public User getUserByUsername(@PathVariable("username") String username){
		User user = null;
		try {
			user = userService.getUserByUsername(username);
		}catch(UserDoesNotExistException e) {
			return null;
		}
		
		return user;
	}
	
	@RequestMapping(value = "/api/user/{userId}", method = RequestMethod.GET)
	public User getUser(@PathVariable("userId") Long userId){
		return userService.getUser(userId);
	}

	@RequestMapping(value = "/api/congregation/{congregationId}/user", method = RequestMethod.POST)
	public User createUser(@PathVariable("congregationId")Long congregationId, @RequestBody User user){
		return userService.createUser(user, congregationId);
	}
	
	@RequestMapping(value = "/api/user/{userId}", method = RequestMethod.PUT)
	public void editUser(@PathVariable("userId") Long userId, @RequestBody User user){
		userService.modifyUser(userId, user);
	}
	
	@RequestMapping(value = "/api/user/{userId}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable("userId") Long userId){
		userService.deleteUser(userId);
	}
}
