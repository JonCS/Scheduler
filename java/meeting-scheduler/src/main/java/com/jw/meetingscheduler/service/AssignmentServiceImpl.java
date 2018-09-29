package com.jw.meetingscheduler.service;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jw.meetingscheduler.exception.AssignmentDoesNotExistException;
import com.jw.meetingscheduler.model.Assignment;
import com.jw.meetingscheduler.model.Congregation;
import com.jw.meetingscheduler.model.MeetingAssignment;
import com.jw.meetingscheduler.model.MinistrySchoolAssignment;
import com.jw.meetingscheduler.model.Publisher;
import com.jw.meetingscheduler.repository.AssignmentRepository;
import com.jw.meetingscheduler.utils.CustomUtils;

@Service
public class AssignmentServiceImpl implements AssignmentService {
	
	@Autowired
	private AssignmentRepository assignmentRepository;
	
	@Autowired
	private PublisherService publisherService;
	
	@Autowired
	private CongregationService congregationService;

	@Override
	public Set<Assignment> getPublisherAssignments(Long publisherId) {
		return publisherService.getPublisherById(publisherId).getAssignments();
	}
	
	@Override
	public Assignment getAssignment(Long assignmentId) {
		Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);
		if(assignment == null)
			throw new AssignmentDoesNotExistException("Assignment with Id " + assignmentId + "does not exist");
		
		return assignment;
	}

	@Override
	public MeetingAssignment createMeetingAssignment(Long publisherId, MeetingAssignment meetingAssignment) {
		Publisher publisher = publisherService.getPublisherById(publisherId);
		meetingAssignment.setPublisher(publisher);
		
		//fix date issue
		fixDate(meetingAssignment);
		
		return assignmentRepository.saveAndFlush(meetingAssignment);
	}

	@Override
	public MinistrySchoolAssignment createMinistrySchoolAssignment(Long publisherId, Long assistantId,
			MinistrySchoolAssignment minSchoolAssignment) {
		Publisher publisher = publisherService.getPublisherById(publisherId);
		minSchoolAssignment.setPublisher(publisher);
		
		if(assistantId != null) {
			Publisher assistant = publisherService.getPublisherById(assistantId);
			minSchoolAssignment.setAssistant(assistant);
		}
		
		//fix date issue
		fixDate(minSchoolAssignment);
		
		return assignmentRepository.saveAndFlush(minSchoolAssignment);
	}

	@Override
	public void editMeetingAssignment(Long publisherId, Long assignmentId, MeetingAssignment meetingAssignment) {
		if(assignmentRepository.existsById(assignmentId)) { 
			MeetingAssignment existing = (MeetingAssignment) assignmentRepository.findById(assignmentId).get();
			
			//fix date issue
			fixDate(meetingAssignment);
			
			CustomUtils.copyProperties(meetingAssignment, existing);
			
			assignmentRepository.saveAndFlush(existing);
		}
		else 
			createMeetingAssignment(publisherId, meetingAssignment); //save assignment if not present
	}

	@Override
	public void editMinistrySchoolAssignment(Long publisherId, Long assistantId, Long assignmentId,
			MinistrySchoolAssignment minSchoolAssignment) {
		if(assignmentRepository.existsById(assignmentId)) { 
			MinistrySchoolAssignment existing = (MinistrySchoolAssignment) assignmentRepository.findById(assignmentId).get();
			
			//fix date issue
			fixDate(minSchoolAssignment);
			
			CustomUtils.copyProperties(minSchoolAssignment, existing);
			
			if(assistantId != null) {
				Publisher assistant = publisherService.getPublisherById(assistantId);
				minSchoolAssignment.setAssistant(assistant);
			}
			
			assignmentRepository.saveAndFlush(existing);
		}
		else 
			createMinistrySchoolAssignment(publisherId, assistantId, minSchoolAssignment); //save assignment if not present
	}
	
	@Override
	public void reassignMeetingAssignment(Long newPublisherId, Long assignmentId) {
		MeetingAssignment assignment = (MeetingAssignment) getAssignment(assignmentId);
		assignment.setPublisher(publisherService.getPublisherById(newPublisherId));
		
		assignmentRepository.saveAndFlush(assignment);
	}

	@Override
	public void reassignMinistrySchoolAssignment(Long newPublisherId, Long newAssistantId, Long assignmentId) {
		MinistrySchoolAssignment assignment = (MinistrySchoolAssignment) getAssignment(assignmentId);
		assignment.setPublisher(publisherService.getPublisherById(newPublisherId));
		if(newAssistantId != null)
			assignment.setAssistant(publisherService.getPublisherById(newAssistantId));
		
		assignmentRepository.saveAndFlush(assignment);
	}

	@Override
	public void deleteAssignment(Long publisherId, Long assignmentId) {
		if(!assignmentRepository.existsById(assignmentId) || 
				!assignmentRepository.findById(assignmentId).get().getPublisher().getId().equals(publisherId))
			throw new AssignmentDoesNotExistException("Assignment with Id " + assignmentId + " does not exist");
		
		assignmentRepository.deleteById(assignmentId);
	}
	
	private void fixDate(Assignment assignment) {
		if(assignment.getDate() != null) {
			Date original = assignment.getDate();
			long hours = 10L * 60L * 60L * 1000L;
			assignment.setDate(new Date(original.getTime() + hours));
		}
	}

	@Override
	public List<Assignment> getAssignmentsByMonth(Long congregationId, int month, int year) {
		return assignmentRepository.getByCongregation_IdAndYearAndMonth(congregationId, year, month);
	}

	@Override
	public List<Assignment> getAssignmentsByDay(Long congregationId, int month, int day, int year) {
		return assignmentRepository.getByCongregation_IdAndYearAndMonthAndDay(congregationId, year, month, day);
	}
}