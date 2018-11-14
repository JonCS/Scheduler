package com.jw.meetingscheduler.service;

import java.util.List;


import com.jw.meetingscheduler.model.User;

public interface UserService {
	
	public User getUser(Long userId);
	
	public User createUser(User user, Long congregationId);
	
	public void modifyUser(Long userId, User user);
	
	public void deleteUser(Long userId);

	public List<User> getUsers();
	
	public User getUserByUsername(String username);
}
