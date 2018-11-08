package com.jw.meetingscheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jw.meetingscheduler.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(value = "select u from User u where u.username =?1")
	public List<User> getByUsername(String username);
	
	@Query(value = "select u from User u where u.email=?1")
	public List<User> getByEmail(String email);

}
