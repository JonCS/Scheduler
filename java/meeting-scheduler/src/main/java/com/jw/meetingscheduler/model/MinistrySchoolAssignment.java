package com.jw.meetingscheduler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ministry_school_assignments")
public class MinistrySchoolAssignment extends Assignment {
	
	@ManyToOne
	@JoinColumn(name="assistant_id")
	@JsonIgnore
	private Publisher assistant;
	
	@Column(name = "study_point")
	private int studyPoint;

	public int getStudyPoint() {
		return studyPoint;
	}

	public void setStudyPoint(int studyPoint) {
		this.studyPoint = studyPoint;
	}

	public Publisher getAssistant() {
		return assistant;
	}

	public void setAssistant(Publisher assistant) {
		this.assistant = assistant;
	}
	
}
