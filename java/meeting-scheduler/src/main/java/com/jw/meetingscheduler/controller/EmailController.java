package com.jw.meetingscheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jw.meetingscheduler.dto.EmailDto;
import com.jw.meetingscheduler.service.EmailService;

@RestController
public class EmailController {
	
	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "/api/sendemail", method = RequestMethod.POST)
	public String testSecure(@RequestBody EmailDto email) {
		emailService.sendSimpleMessage(email.getRecipient(), email.getSubject(), email.getBody());
		return "Email sent successfully";
	}
}
