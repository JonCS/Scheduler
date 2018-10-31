package com.jw.meetingscheduler.dto;

import com.jw.meetingscheduler.model.Publisher;

public class AssignmentPublishersDto {

	private Publisher publisher;
	private Publisher assistant;
	
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	public Publisher getAssistant() {
		return assistant;
	}
	public void setAssistant(Publisher assistant) {
		this.assistant = assistant;
	}	
}
