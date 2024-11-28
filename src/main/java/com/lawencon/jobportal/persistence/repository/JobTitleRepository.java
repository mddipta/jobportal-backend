package com.lawencon.jobportal.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.JobTitle;

@Repository
public interface JobTitleRepository
                extends JpaRepository<JobTitle, String>, JpaSpecificationExecutor<JobTitle> {

        Long countBy();
}
