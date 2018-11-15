package com.jw.meetingscheduler.controller;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jw.meetingscheduler.dto.EmailDto;
import com.jw.meetingscheduler.model.Assignment;
import com.jw.meetingscheduler.model.Congregation;
import com.jw.meetingscheduler.model.MeetingAssignment;
import com.jw.meetingscheduler.model.MinistrySchoolAssignment;
import com.jw.meetingscheduler.service.AssignmentService;
import com.jw.meetingscheduler.service.CongregationService;
import com.jw.meetingscheduler.service.EmailService;
import com.jw.meetingscheduler.service.SettingService;

@RestController
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	private CongregationService congregationService;
	
	@Autowired
	private SettingService settingService;

	@RequestMapping(value = "/api/sendemail", method = RequestMethod.POST)
	public String sendEmail(@RequestBody EmailDto email) {
		emailService.sendSimpleMessage(email.getRecipient(), email.getSubject(), email.getBody());
		return "Email sent successfully";
	}
	
	@Scheduled(cron = "0 1 0 * * *", zone="America/Los_Angeles")
	@RequestMapping(value = "/api/sendemail/interval", method = RequestMethod.GET)
	public String sendEmailsAtInterval() {	
		//send email reminders for each congregation based on their email settings
		for(Congregation cong: congregationService.getCongregations()) {
			//get email reminder time setting
			Long emailFrequency = Long.parseLong(settingService.getSetting(cong.getId(), "emailFrequency").getValue());
			 
			List<Assignment> assignments = assignmentService.getUpcomingCongregationAssignments(cong.getId());
			for(Assignment a: assignments) {
				java.util.Date startDate = new java.util.Date();
				java.util.Date endDate = new java.util.Date(a.getDate().getTime());
				
				Long daysBetween = ChronoUnit.DAYS.between(startDate.toInstant(),endDate.toInstant()) + 1;
				if(daysBetween.equals(emailFrequency)) {
					//send email
					java.sql.Date d = a.getDate();
					SimpleDateFormat simpDate = new SimpleDateFormat("MM-dd-YYYY");
					String date = simpDate.format(d);
					
					String to = a.getPublisher().getEmail();
					String subject = "Reminder - " + a.getAssignmentType() + " on " + date;
					String body = "Hi " + a.getPublisher().getFirstName() + " " + a.getPublisher().getLastName() + ",\n\n" + 
							"This is a friendly reminder that you have an assignment on " + date + ". Please note the assignment details:\n" +
							"\t-Assignment type: " + a.getAssignmentType() + "\n" +
						    "\t-Date: " + date + "\n";
					
					if(a instanceof MeetingAssignment) {
						body += "\t-Duration: " + ((MeetingAssignment)a).getDuration() + " minutes\n";
						if(((MeetingAssignment)a).getNotes() != null && ((MeetingAssignment)a).getNotes().trim().length() > 0)
							body += "\t-Notes: " + ((MeetingAssignment)a).getNotes() + "\n";
					}
					else if(a instanceof MinistrySchoolAssignment) {
						MinistrySchoolAssignment assign = (MinistrySchoolAssignment)a;
						body += "\t-Study Point: " + assign.getStudyPoint() + "\n";
						
						if(assign.getAssistant() != null) 
							body += "\t-Publisher: " + assign.getPublisher().getFirstName() + " " + assign.getPublisher().getLastName() + "\n"
							+ "\t-Assistant: " + assign.getAssistant().getFirstName() + " " + assign.getAssistant().getLastName() + "\n";
					}
					
					body += "\nSincerely,\nAssignment Scheduler";
					
					if(a instanceof MinistrySchoolAssignment && ((MinistrySchoolAssignment)a).getAssistant() != null) {
						MinistrySchoolAssignment assign = (MinistrySchoolAssignment)a;
						
						//send email to assistant publisher
						String assistantEmailBody = body;
						assistantEmailBody = assistantEmailBody.replaceAll("\\b" + a.getPublisher().getFirstName() + " " + a.getPublisher().getLastName() + "\\b", assign.getAssistant().getFirstName() + " " + assign.getAssistant().getLastName());
						emailService.sendSimpleMessage(assign.getAssistant().getEmail(), subject, assistantEmailBody);
					}
					
					emailService.sendSimpleMessage(to, subject, body);
					
				}
						
			}
		}
		
		return "Success!";
		
	}
}
