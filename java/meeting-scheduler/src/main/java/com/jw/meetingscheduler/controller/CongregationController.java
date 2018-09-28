package com.jw.meetingscheduler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jw.meetingscheduler.model.Congregation;
import com.jw.meetingscheduler.service.CongregationService;

@RestController
public class CongregationController {
	
	@Autowired
	private CongregationService congregationService;
	
	@RequestMapping(value = "/api/congregations", method = RequestMethod.GET)
	public List<Congregation> getCongregations(){
		return congregationService.getCongregations();
	}
	
	@RequestMapping(value = "/api/congregations/{congregationId}", method = RequestMethod.GET)
	public Congregation getCongregation(@PathVariable("congregationId") Long congregationId){
		return congregationService.getCongregation(congregationId);
	}
	
	@RequestMapping(value = "/api/congregation", method = RequestMethod.POST)
	public Congregation createCongregation(@RequestBody Congregation congregation) {
		return congregationService.createCongregation(congregation);
	}
	
	@RequestMapping(value = "/api/congregation/{congregationId}", method = RequestMethod.PUT)
	public void updateCongregation(@RequestBody Congregation congregation, @PathVariable("congregationId") Long congregationId) {
		congregationService.updateCongregation(congregation, congregationId);
	}
	
	@RequestMapping(value = "/api/congregation/{congregationId}", method = RequestMethod.DELETE)
	public void deleteCongregation(@PathVariable("congregationId") Long congregationId) {
		congregationService.deleteCongregation(congregationId);
	}
	
}
