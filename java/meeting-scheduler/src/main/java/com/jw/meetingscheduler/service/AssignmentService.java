package com.jw.meetingscheduler.service;

import java.util.List;
import java.util.Set;

import com.jw.meetingscheduler.dto.AssignmentPublishersDto;
import com.jw.meetingscheduler.model.Assignment;
import com.jw.meetingscheduler.model.MeetingAssignment;
import com.jw.meetingscheduler.model.MinistrySchoolAssignment;

public interface AssignmentService {
	
	public Set<Assignment> getPublisherAssignments(Long publisherId, Integer dateRange);
	
	public List<Assignment> getCongregationAssignments(Long congregationId, String assignmentType);
	
	public Assignment getAssignment(Long assignmentId);
	
	public List<Assignment> getAssignmentsByMonth(Long congregationId, int month, int year);
	
	public List<Assignment> getAssignmentsByDay(Long congregationId, int month, int day, int year);
	
	public MeetingAssignment createMeetingAssignment(Long publisherId, MeetingAssignment meetingAssignment);

	public MinistrySchoolAssignment createMinistrySchoolAssignment(Long publisherId, Long assistantId, MinistrySchoolAssignment minSchoolAssignment);
	
	public MeetingAssignment editMeetingAssignment(Long publisherId, Long assignmentId, MeetingAssignment meetingAssignment);
	
	public MinistrySchoolAssignment editMinistrySchoolAssignment(Long publisherId, Long assistantId, Long assignmentId, MinistrySchoolAssignment minSchoolAssignment);
	
	public void reassignMeetingAssignment(Long newPublisherId, Long assignmentId);
	
	public void reassignMinistrySchoolAssignment(Long newPublisherId, Long newAssistantId, Long assignmentId);
	
	public Assignment deleteAssignment(Long assignmentId);

	public AssignmentPublishersDto getAssignmentPublishers(Long assignmentId);
	
}
