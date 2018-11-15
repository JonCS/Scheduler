package com.jw.meetingscheduler.exception;

public class SettingDoesNotExistException extends RuntimeException {

	public SettingDoesNotExistException() {
	}

	public SettingDoesNotExistException(String message) {
		super(message);
	}

}
