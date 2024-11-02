package com.lawencon.jobportal.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.JobVacancy;

@Repository
public interface JobVacancyRepository extends JpaRepository<JobVacancy, String> {

}
