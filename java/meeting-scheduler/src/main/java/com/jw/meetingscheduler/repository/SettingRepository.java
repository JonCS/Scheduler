package com.jw.meetingscheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jw.meetingscheduler.model.Setting;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {

	@Query(value = "select s from Setting s where s.congregation.id = ?1 and property =?2")
	public List<Setting> getByCongregation_IdAndSettingProperty(Long congregationId, String property);
}
