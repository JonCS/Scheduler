package com.jw.meetingscheduler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jw.meetingscheduler.exception.PublisherDoesNotExistException;
import com.jw.meetingscheduler.model.Congregation;
import com.jw.meetingscheduler.model.Publisher;
import com.jw.meetingscheduler.repository.PublisherRepository;
import com.jw.meetingscheduler.utils.CustomUtils;

@Service
public class PublisherServiceImpl implements PublisherService {
	
	@Autowired
	private PublisherRepository publisherRepository;
	
	@Autowired
	private CongregationService congregationService;

	@Override
	public List<Publisher> getPublishers(Long congregationId, String sortMethod) {
		String colName = "";
		if(sortMethod == null)
			return congregationService.getCongregation(congregationId).getPublishers();
		else if(sortMethod.equalsIgnoreCase("firstName"))
			colName = "firstName";
		else if(sortMethod.equalsIgnoreCase("lastName"))
			colName = "lastName";
		
		List<Publisher> publishers = publisherRepository.findAll(new Sort(Sort.Direction.ASC, colName)); //get sorted list of publishers
		
		//remove publishers that are not from that congregation
		for(int i = publishers.size() - 1; i > -1; i--)
			if(!publishers.get(i).getCongregation().getId().equals(congregationId))
				publishers.remove(i);
		return publishers;
	}
	
	@Override
	public Publisher getPublisherById(Long publisherId) {
		Publisher publisher = publisherRepository.findById(publisherId).orElse(null);
		if(publisher == null)
			throw new PublisherDoesNotExistException("Publisher with Id " + publisherId + " does not exist");
		
		return publisher;
	}
	
	@Override
	public Publisher createPublisher(Long congregationId, Publisher publisher) {
		Congregation congregation = congregationService.getCongregation(congregationId);
		publisher.setCongregation(congregation);
		
		return publisherRepository.saveAndFlush(publisher);
	}

	@Override
	public void editPublisher(Long congregationId, Long publisherId, Publisher publisher) {
		if(publisherRepository.existsById(publisherId) && publisherRepository.findById(publisherId).get().getCongregation().getId().equals(congregationId)) {
			Publisher existing = publisherRepository.findById(publisherId).get();
			CustomUtils.copyProperties(publisher, existing);
			publisherRepository.saveAndFlush(existing);
		}	
		else
			createPublisher(congregationId, publisher);
	}

	@Override
	public void deletePublisher(Long publisherId) {
		if(publisherRepository.existsById(publisherId))
			throw new PublisherDoesNotExistException("Publisher with Id " + publisherId + " does not exist");
		
		publisherRepository.deleteById(publisherId);
	}

}
