package com.jw.meetingscheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jw.meetingscheduler.model.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long>{

	@Query("select a from Assignment a where a.publisher.congregation.id = ?1 and year(a.date) = ?2 and month(a.date) = ?3")
	public List<Assignment> getByCongregation_IdAndYearAndMonth(Long congregationId, int year, int month);
	
	@Query("select a from Assignment a where a.publisher.congregation.id = ?1 and year(a.date) = ?2 and month(a.date) = ?3 and day(a.date) = ?4")
	public List<Assignment> getByCongregation_IdAndYearAndMonthAndDay(Long congregationId, int year, int month, int day);
}
