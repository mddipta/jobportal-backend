package com.lawencon.jobportal.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.JobSpecification;

@Repository
public interface JobSpecificationRepository extends JpaRepository<JobSpecification, String>,
        JpaSpecificationExecutor<JobSpecification> {
    List<JobSpecification> findByJobTitleId(String jobTitleId);

    void deleteByJobTitle_Id(String jobTitleId);
}
