package com.jw.meetingscheduler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jw.meetingscheduler.exception.UserAlreadyExistsException;
import com.jw.meetingscheduler.exception.UserDoesNotExistException;
import com.jw.meetingscheduler.model.User;
import com.jw.meetingscheduler.repository.UserRepository;
import com.jw.meetingscheduler.utils.CustomUtils;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Override
	public User getUser(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		if(user == null)
			throw new UserDoesNotExistException("User does not exist");
		return user;
	}

	@Override
	public User createUser(User user) {
		if(!userRepository.getByEmail(user.getEmail()).isEmpty() || !userRepository.getByUsername(user.getUsername()).isEmpty())
			throw new UserAlreadyExistsException("User already exists");
			
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userRepository.saveAndFlush(user);
	}

	@Override
	public void modifyUser(Long userId, User user) {
		if(userRepository.existsById(userId)) {
			User existing = userRepository.findById(userId).get();
			CustomUtils.copyProperties(user, existing);
			
			if(existing.getPassword() != null && existing.getPassword().length() > 0)
				existing.setPassword(bcryptEncoder.encode(existing.getPassword())); 
				
			userRepository.saveAndFlush(existing);
		}	
		else
			createUser(user);
	}

	@Override
	public void deleteUser(Long userId) {
		if(!userRepository.existsById(userId))
			throw new UserDoesNotExistException("User does not exist");
		
		userRepository.deleteById(userId);
		
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserByUsername(String username) {
		List<User> result = userRepository.getByUsername(username);
		if(result.isEmpty())
			throw new UserDoesNotExistException("User does not exist"); 
		
		return result.get(0);
	}
	
}
