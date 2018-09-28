package com.jw.meetingscheduler.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jw.meetingscheduler.model.Publisher;
import com.jw.meetingscheduler.service.PublisherService;

@RestController
public class PublisherController {
	
	@Autowired
	private PublisherService publisherService;
	
	@RequestMapping(value = "/api/congregation/{congregationId}/publishers", method = RequestMethod.GET)
	public List<Publisher> getPublishers(@PathVariable("congregationId") Long congregationId, 
			@RequestParam("sortMethod") Optional<String> sortMethod){
		return publisherService.getPublishers(congregationId, sortMethod.orElse(null));
	}
	
	@RequestMapping(value = "/api/publisher/{publisherId}", method = RequestMethod.GET)
	public Publisher getPublisher(@PathVariable("publisherId") Long publisherId){
		return publisherService.getPublisherById(publisherId);
	}
	
	@RequestMapping(value = "/api/congregation/{congregationId}/publisher", method = RequestMethod.POST)
	public Publisher createPublisher(@PathVariable("congregationId") Long congregationId, @RequestBody Publisher publisher) {
		return publisherService.createPublisher(congregationId, publisher);
	}
	
	@RequestMapping(value = "/api/congregation/{congregationId}/publisher/{publisherId}", method = RequestMethod.PUT)
	public void updatePublisher(@PathVariable("congregationId") Long congregationId, @PathVariable("publisherId") Long publisherId,  @RequestBody Publisher publisher) {
		publisherService.editPublisher(congregationId, publisherId, publisher);
	}
	
	@RequestMapping(value = "/api/publisher/{publisherId}", method = RequestMethod.DELETE)
	public void deletePublisher(@PathVariable("publisherId") Long publisherId) {
		publisherService.deletePublisher(publisherId);
	}

}
