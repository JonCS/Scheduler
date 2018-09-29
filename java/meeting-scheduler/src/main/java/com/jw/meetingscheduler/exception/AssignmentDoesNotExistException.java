package com.jw.meetingscheduler.exception;

public class AssignmentDoesNotExistException extends RuntimeException{

	public AssignmentDoesNotExistException() {
		super();
	}

	public AssignmentDoesNotExistException(String message) {
		super(message);
	}

}
