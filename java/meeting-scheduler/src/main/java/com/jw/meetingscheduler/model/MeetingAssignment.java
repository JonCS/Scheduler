package com.jw.meetingscheduler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "meeting_assignments")
public class MeetingAssignment extends Assignment {

	@Column(name = "duration")
	private int duration;
	
	@Column(name = "notes")
	private String notes;

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
