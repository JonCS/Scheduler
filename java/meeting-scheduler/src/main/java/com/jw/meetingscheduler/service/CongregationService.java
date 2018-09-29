package com.jw.meetingscheduler.service;

import java.util.List;

import com.jw.meetingscheduler.model.Congregation;

public interface CongregationService {
	
	public List<Congregation> getCongregations();
	
	public Congregation createCongregation(Congregation congregation);
	
	public void updateCongregation(Congregation congregation, Long id);
	
	public void deleteCongregation(Long id);
	
	public Congregation getCongregation(Long congregationId);
}
