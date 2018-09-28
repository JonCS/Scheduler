package com.jw.meetingscheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jw.meetingscheduler.model.Congregation;

@Repository
public interface CongregationRepository extends JpaRepository<Congregation, Long>{

}
