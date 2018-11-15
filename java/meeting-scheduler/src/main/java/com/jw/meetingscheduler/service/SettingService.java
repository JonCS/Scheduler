package com.jw.meetingscheduler.service;

import com.jw.meetingscheduler.model.Setting;

public interface SettingService {

	public Setting getSetting(Long congregationId, String settingName);

	public Setting createSetting(Long congregationId, Setting setting);

	public Setting modifySetting(Long congregationId, String settingName, Setting setting);

	public void deleteSetting(Long congregationId, String settingName);
}
