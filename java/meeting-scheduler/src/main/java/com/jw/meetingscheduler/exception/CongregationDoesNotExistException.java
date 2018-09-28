package com.jw.meetingscheduler.exception;

public class CongregationDoesNotExistException extends RuntimeException {

	public CongregationDoesNotExistException() {
		super();
	}

	public CongregationDoesNotExistException(String message) {
		super(message);
	}	
}
