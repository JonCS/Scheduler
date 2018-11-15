package com.jw.meetingscheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jw.meetingscheduler.model.Setting;
import com.jw.meetingscheduler.service.SettingService;

@RestController
public class SettingController {

	@Autowired
	private SettingService settingService;
	
	@RequestMapping(value = "/api/congregation/{congregationId}/setting/{settingName}", method = RequestMethod.GET)
	public Setting getSetting(@PathVariable("congregationId") Long congregationId, @PathVariable("settingName") String settingName){
		return settingService.getSetting(congregationId, settingName);
	}

	@RequestMapping(value = "/api/congregation/{congregationId}/setting", method = RequestMethod.POST)
	public Setting createSetting(@PathVariable("congregationId") Long congregationId, @RequestBody Setting setting){
		return settingService.createSetting(congregationId, setting);
	}
	
	@RequestMapping(value = "/api/congregation/{congregationId}/setting/{settingName}", method = RequestMethod.PUT)
	public void editUser(@PathVariable("congregationId") Long congregationId, @PathVariable("settingName") String settingName, @RequestBody Setting setting){
		settingService.modifySetting(congregationId, settingName, setting);
	}
	
	@RequestMapping(value = "/api/congregation/{congregationId}/setting/{settingName}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable("congregationId") Long congregationId, @PathVariable("settingName") String settingName){
		settingService.deleteSetting(congregationId, settingName);
	}
}
