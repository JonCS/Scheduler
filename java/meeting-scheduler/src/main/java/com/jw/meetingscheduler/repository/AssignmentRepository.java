package com.jw.meetingscheduler.repository;

import java.sql.Date;
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
	
	@Query(value = "select a from Assignment a where a.publisher.congregation.id = ?1 AND TYPE(a) = MeetingAssignment")
	public List<Assignment> getMeetingAssignmentsByCongregation_Id(Long congregationId);
	
	@Query(value = "select a from Assignment a where a.publisher.congregation.id = ?1 AND TYPE(a) = MinistrySchoolAssignment")
	public List<Assignment> getMinistrySchoolAssignmentsByCongregation_Id(Long congregationId);
	
	@Query(value = "select a from Assignment a where a.publisher.congregation.id = ?1")
	public List<Assignment> getByCongregation_Id(Long congregationId);
	
	@Query("select a from Assignment a where a.assignmentType =?1 and year(a.date) = ?2 and month(a.date) = ?3 and day(a.date) = ?4")
	public List<Assignment> getByAssignment_TypeAndYearAndMonthAndDay(String assignmentType, int year, int month, int day);
	
	@Query("select a from Assignment a where a.publisher.id =?1 and a.date between ?2 and ?3")
	public List<Assignment> getByPublisher_IdAndBetweenDates(Long publisherId, Date beginDate, Date endDate);
	
	@Query("select a from Assignment a where a.publisher.congregation.id =?1 and a.date > ?2")
	public List<Assignment> getByCongregation_IdAndDateAfter(Long congregationId, Date beginDate);
}
