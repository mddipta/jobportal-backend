package com.lawencon.jobportal.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.jobportal.persistence.entity.JobVacancyTransaction;

@Repository
public interface JobVacancyTransactionRepository
                extends JpaRepository<JobVacancyTransaction, String> {
        Optional<JobVacancyTransaction> findTopByJobVacancyIdOrderByCreatedAtDesc(
                        String jobVacancyId);
}
