package com.lawencon.jobportal.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.JobStatus;

@Repository
public interface JobStatusRepository extends JpaRepository<JobStatus, String> {

    Optional<JobStatus> findByCode(String code);
}
