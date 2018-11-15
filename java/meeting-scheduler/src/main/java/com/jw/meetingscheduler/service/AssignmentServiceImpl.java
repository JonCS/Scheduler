package com.jw.meetingscheduler.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jw.meetingscheduler.dto.AssignmentPublishersDto;
import com.jw.meetingscheduler.exception.AssignmentDoesNotExistException;
import com.jw.meetingscheduler.model.Assignment;
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

	@Override
	public Set<Assignment> getPublisherAssignments(Long publisherId, Integer dateRange) {
		if(dateRange == null)
			return publisherService.getPublisherById(publisherId).getAssignments();
			
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -dateRange);
		java.sql.Date beginDate = CustomUtils.convertFromJAVADateToSQLDate(calendar.getTime());
		
		calendar.add(Calendar.DAY_OF_MONTH, dateRange * 2);
		java.sql.Date endDate = CustomUtils.convertFromJAVADateToSQLDate(calendar.getTime());
		
		List<Assignment> result = assignmentRepository.getByPublisher_IdAndBetweenDates(publisherId, beginDate, endDate);
		
		return new TreeSet<Assignment>(result);
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
		LocalDate localDate = meetingAssignment.getDate().toLocalDate();

		if(assignmentRepository.getByAssignment_TypeAndYearAndMonthAndDay(meetingAssignment.getAssignmentType(), localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()).size() != 0) {
			MeetingAssignment existing = (MeetingAssignment) assignmentRepository.getByAssignment_TypeAndYearAndMonthAndDay(meetingAssignment.getAssignmentType(), localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()).get(0);
			CustomUtils.copyProperties(meetingAssignment, existing);
			assignmentRepository.saveAndFlush(existing);
			return assignmentRepository.saveAndFlush(existing);
		}
			
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
		LocalDate localDate = minSchoolAssignment.getDate().toLocalDate();
		if(assignmentRepository.getByAssignment_TypeAndYearAndMonthAndDay(minSchoolAssignment.getAssignmentType(), localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()).size() != 0) {
			MinistrySchoolAssignment existing = (MinistrySchoolAssignment) assignmentRepository.getByAssignment_TypeAndYearAndMonthAndDay(minSchoolAssignment.getAssignmentType(), localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()).get(0);
			CustomUtils.copyProperties(minSchoolAssignment, existing);
			assignmentRepository.saveAndFlush(existing);
			return assignmentRepository.saveAndFlush(existing);
		}
		
		return assignmentRepository.saveAndFlush(minSchoolAssignment);
	}

	@Override
	public MeetingAssignment editMeetingAssignment(Long publisherId, Long assignmentId, MeetingAssignment meetingAssignment) {
		if(assignmentRepository.existsById(assignmentId)) { 
			MeetingAssignment existing = (MeetingAssignment) assignmentRepository.findById(assignmentId).get();
			
			//fix date issue
			fixDate(meetingAssignment);
			
			CustomUtils.copyProperties(meetingAssignment, existing);
			existing.setPublisher(publisherService.getPublisherById(publisherId));
			
			return assignmentRepository.saveAndFlush(existing);
		}
		else 
			return createMeetingAssignment(publisherId, meetingAssignment); //save assignment if not present
	}

	@Override
	public MinistrySchoolAssignment editMinistrySchoolAssignment(Long publisherId, Long assistantId, Long assignmentId,
			MinistrySchoolAssignment minSchoolAssignment) {
		if(assignmentRepository.existsById(assignmentId)) { 
			MinistrySchoolAssignment existing = (MinistrySchoolAssignment) assignmentRepository.findById(assignmentId).get();
			
			//fix date issue
			fixDate(minSchoolAssignment);
			
			CustomUtils.copyProperties(minSchoolAssignment, existing);
			
			existing.setPublisher(publisherService.getPublisherById(publisherId));
			if(assistantId != null) {
				Publisher assistant = publisherService.getPublisherById(assistantId);
				existing.setAssistant(assistant);
			}
			
			return assignmentRepository.saveAndFlush(existing);
		}
		else 
			return createMinistrySchoolAssignment(publisherId, assistantId, minSchoolAssignment); //save assignment if not present
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
	public Assignment deleteAssignment( Long assignmentId) {
		if(!assignmentRepository.existsById(assignmentId))
			throw new AssignmentDoesNotExistException("Assignment with Id " + assignmentId + " does not exist");
		
		Assignment assignment = getAssignment(assignmentId);
		assignmentRepository.deleteById(assignmentId);
		
		return assignment;
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

	@Override
	public List<Assignment> getCongregationAssignments(Long congregationId, String assignmentType) {
		if(assignmentType.equalsIgnoreCase("MinistrySchoolAssignment"))
			return assignmentRepository.getMinistrySchoolAssignmentsByCongregation_Id(congregationId);
		else if(assignmentType.equalsIgnoreCase("MeetingAssignment"))
			return assignmentRepository.getMeetingAssignmentsByCongregation_Id(congregationId);
		else
			return assignmentRepository.getByCongregation_Id(congregationId);
		
	}

	@Override
	public AssignmentPublishersDto getAssignmentPublishers(Long assignmentId) {
		AssignmentPublishersDto assignmentPublishersDto = new AssignmentPublishersDto();
		
		Assignment assignment = getAssignment(assignmentId);
		assignmentPublishersDto.setPublisher(assignment.getPublisher());
		if(assignment instanceof MinistrySchoolAssignment)
			assignmentPublishersDto.setAssistant(((MinistrySchoolAssignment)assignment).getAssistant());
			
		return assignmentPublishersDto;
	}

	@Override
	public List<Assignment> getUpcomingCongregationAssignments(Long congregationId) {
		return assignmentRepository.getByCongregation_IdAndDateAfter(congregationId, CustomUtils.convertFromJAVADateToSQLDate(new java.util.Date()));
	}
}
