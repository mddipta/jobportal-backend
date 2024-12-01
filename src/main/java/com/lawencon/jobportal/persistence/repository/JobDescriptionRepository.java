package com.lawencon.jobportal.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.JobDescription;

@Repository
public interface JobDescriptionRepository
        extends JpaRepository<JobDescription, String>, JpaSpecificationExecutor<JobDescription> {
    List<JobDescription> findByTitleJobId(String jobSpecificationId);

    void deleteByTitleJobId(String jobTitleId);
}
