package com.jw.meetingscheduler.exception;

public class PublisherDoesNotExistException extends RuntimeException {

	public PublisherDoesNotExistException() {
		super();
	}

	public PublisherDoesNotExistException(String message) {
		super(message);
	}
}
