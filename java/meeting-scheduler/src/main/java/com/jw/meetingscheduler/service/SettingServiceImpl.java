package com.jw.meetingscheduler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jw.meetingscheduler.exception.SettingDoesNotExistException;
import com.jw.meetingscheduler.model.Setting;
import com.jw.meetingscheduler.repository.SettingRepository;
import com.jw.meetingscheduler.utils.CustomUtils;

@Service
public class SettingServiceImpl implements SettingService {
	
	@Autowired
	private SettingRepository settingRepository;
	
	@Autowired
	private CongregationService congregationService;

	@Override
	public Setting getSetting(Long congregationId, String settingName) {
		List<Setting> results = settingRepository.getByCongregation_IdAndSettingProperty(congregationId, settingName);
		if(results.isEmpty())
			throw new SettingDoesNotExistException("Setting with name " + settingName + " does not exist.");
		
		return results.get(0);
	}

	@Override
	public Setting createSetting(Long congregationId, Setting setting) {
		if(settingRepository.getByCongregation_IdAndSettingProperty(congregationId, setting.getProperty()).isEmpty()) {
			setting.setCongregation(congregationService.getCongregation(congregationId));
			return settingRepository.saveAndFlush(setting);
		}
		else
			return modifySetting(congregationId, setting.getProperty(), setting);
	}

	@Override
	public Setting modifySetting(Long congregationId, String settingName, Setting setting) {
		Setting existing = getSetting(congregationId, settingName);
		CustomUtils.copyProperties(setting, existing);
		return settingRepository.saveAndFlush(existing);	
	}

	@Override
	public void deleteSetting(Long congregationId, String settingName) {
		Setting s = getSetting(congregationId, settingName);
		settingRepository.delete(s);
	}

	
}
