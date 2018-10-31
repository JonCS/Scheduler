package com.jw.meetingscheduler.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jw.meetingscheduler.dto.AssignmentPublishersDto;
import com.jw.meetingscheduler.model.Assignment;
import com.jw.meetingscheduler.model.MeetingAssignment;
import com.jw.meetingscheduler.model.MinistrySchoolAssignment;
import com.jw.meetingscheduler.service.AssignmentService;

@RestController
public class AssignmentController {

	@Autowired
	private AssignmentService assignmentService;
	
	//GET APIs
	
	@RequestMapping(value = "/api/publisher/{publisherId}/assignments", method = RequestMethod.GET)
	public Set<Assignment> getPublisherAssignments(@PathVariable("publisherId") Long publisherId){
		return assignmentService.getPublisherAssignments(publisherId);
	}
	
	@RequestMapping(value = "/api/assignment/{assignmentId}", method = RequestMethod.GET)
	public Assignment getAssignment(@PathVariable("assignmentId") Long assignmentId){
		return assignmentService.getAssignment(assignmentId);
	}
	
	@RequestMapping(value = "/api/{congregationId}/assignments", method = RequestMethod.GET)
	public List<Assignment> getCongregationAssignments(@PathVariable("congregationId") Long congregationId,
			@RequestParam("assignmentType") Optional<String> assignmentType){
		return assignmentService.getCongregationAssignments(congregationId, assignmentType.orElse(""));
	}
	
	@RequestMapping(value = "/api/congregation/{congregationId}/assignments/{calendarMonth}/{calendarYear}", method = RequestMethod.GET)
	public List<Assignment> getAssignmentsForMonth(@PathVariable("congregationId") Long congregationId,
			@PathVariable("calendarMonth") int month,
			@PathVariable("calendarYear") int year){
		return assignmentService.getAssignmentsByMonth(congregationId, month, year);
	}
	
	@RequestMapping(value = "/api/congregation/{congregationId}/assignments/{calendarMonth}/{calendarDay}/{calendarYear}", method = RequestMethod.GET)
	public List<Assignment> getAssignmentsForDay(@PathVariable("congregationId") Long congregationId,
			@PathVariable("calendarMonth") int month,
			@PathVariable("calendarDay") int day,
			@PathVariable("calendarYear") int year){
		return assignmentService.getAssignmentsByDay(congregationId, month, day, year);
	}
	
	@RequestMapping(value = "/api/assignment/{assignmentId}/publishers", method = RequestMethod.GET)
	public AssignmentPublishersDto getPublishersForAssignment(@PathVariable("assignmentId") Long assignmentId) {
		return assignmentService.getAssignmentPublishers(assignmentId);
	}
	
	//POST APIS
	
	@RequestMapping(value = "/api/publisher/{publisherId}/MeetingAssignment", method = RequestMethod.POST)
	public MeetingAssignment createMeetingAssignment(@RequestBody MeetingAssignment meetingAssignment, 
			@PathVariable("publisherId") Long publisherId) {
		return assignmentService.createMeetingAssignment(publisherId, meetingAssignment);
	}
	
	@RequestMapping(value = "/api/publisher/{publisherId}/MinistrySchoolAssignment", method = RequestMethod.POST)
	public MinistrySchoolAssignment createMinistrySchoolAssignment(@RequestBody MinistrySchoolAssignment minSchoolAssignment, 
			@PathVariable("publisherId") Long publisherId, 
			@RequestParam("assistantId") Optional<Long> assistantId) {
		return assignmentService.createMinistrySchoolAssignment(publisherId, assistantId.orElse(null), minSchoolAssignment);
	}
	
	//PUT APIs
	
	@RequestMapping(value = "/api/publisher/{publisherId}/MeetingAssignment/{assignmentId}", method = RequestMethod.PUT)
	public void editMeetingAssignment(@RequestBody MeetingAssignment meetingAssignment, 
			@PathVariable("publisherId") Long publisherId, 
			@PathVariable("assignmentId") Long assignmentId) {
		assignmentService.editMeetingAssignment(publisherId, assignmentId, meetingAssignment);
	}
	
	@RequestMapping(value = "/api/publisher/{publisherId}/MinistrySchoolAssignment/{assignmentId}", method = RequestMethod.PUT)
	public void editMinistrySchoolAssignment(@RequestBody MinistrySchoolAssignment minSchoolAssignment, 
			@PathVariable("publisherId") Long publisherId, 
			@PathVariable("assignmentId") Long assignmentId,
			@RequestParam("assistantId") Optional<Long> assistantId) {
		assignmentService.editMinistrySchoolAssignment(publisherId, assistantId.orElse(null), assignmentId, minSchoolAssignment);
	}
	
	@RequestMapping(value = "/api/MeetingAssignment/{assignmentId}/publisher/{newPublisherId}", method = RequestMethod.PUT)
	public void reassignMeetingAssignment(@PathVariable("newPublisherId") Long newPublisherId, 
			@PathVariable("assignmentId") Long assignmentId) {
		assignmentService.reassignMeetingAssignment(newPublisherId, assignmentId);
	}
	
	@RequestMapping(value = "/api/MinistrySchoolAssignment/{assignmentId}/publisher/{newPublisherId}", method = RequestMethod.PUT)
	public void reassignMinistrySchoolAssignment(@PathVariable("newPublisherId") Long newPublisherId, 
			@PathVariable("assignmentId") Long assignmentId,
			@RequestParam("newAssistantId") Optional<Long> newAssistantId) {
		assignmentService.reassignMinistrySchoolAssignment(newPublisherId, newAssistantId.orElse(null), assignmentId);
	}
	
	//DELETE APIs
	
	@RequestMapping(value = "/api/assignment/{assignmentId}", method = RequestMethod.DELETE)
	public Assignment deleteAssignment(@PathVariable("assignmentId") Long assignmentId) {
		return assignmentService.deleteAssignment(assignmentId);
	}

}
