package com.jw.meetingscheduler.service;

import java.util.List;

import com.jw.meetingscheduler.model.Publisher;

public interface PublisherService {
	
	public List<Publisher> getPublishers(Long congregationId, String sortMethod);
	
	public Publisher createPublisher(Long congregationId, Publisher publisher);
	
	public void editPublisher(Long congregationId, Long publisherId, Publisher publisher);
	
	public void deletePublisher(Long publisherId);
	
	public Publisher getPublisherById(Long publisherId);

}
