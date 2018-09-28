package com.jw.meetingscheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jw.meetingscheduler.model.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

}
