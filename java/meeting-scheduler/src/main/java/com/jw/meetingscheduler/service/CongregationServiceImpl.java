package com.jw.meetingscheduler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jw.meetingscheduler.exception.CongregationDoesNotExistException;
import com.jw.meetingscheduler.exception.CongregationExistsException;
import com.jw.meetingscheduler.model.Congregation;
import com.jw.meetingscheduler.repository.CongregationRepository;
import com.jw.meetingscheduler.utils.CustomUtils;

@Service
public class CongregationServiceImpl implements CongregationService {
	
	@Autowired
	private CongregationRepository congregationRepository;

	@Override
	public List<Congregation> getCongregations() {
		return congregationRepository.findAll();
	}

	@Override
	public Congregation createCongregation(Congregation congregation) {
		//throw an exception if a congregation with the same name already exists
		for(Congregation c: getCongregations())
			if(c.getName().equals(congregation.getName()))
				throw new CongregationExistsException("Congregation with name " + congregation.getName() + " already exists");
		
		return congregationRepository.saveAndFlush(congregation);
	}
	
	@Override
	public void updateCongregation(Congregation congregation, Long id) {
		if(congregationRepository.findById(id).orElse(null) == null)
			createCongregation(congregation);
		else {
			Congregation existing = congregationRepository.findById(id).get();
			CustomUtils.copyProperties(congregation, existing);
			congregationRepository.saveAndFlush(existing);
		}		
	}

	@Override
	public void deleteCongregation(Long id) {
		congregationRepository.deleteById(id);
	}

	@Override
	public Congregation getCongregation(Long congregationId) {
		Congregation congregation = congregationRepository.findById(congregationId).orElse(null);
		if(congregation == null)
			throw new CongregationDoesNotExistException("Congregation with Id " + congregationId + " does not exist");
		
		return congregation;
	}

}
