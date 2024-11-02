package com.lawencon.jobportal.service;

import java.util.Optional;

import com.lawencon.jobportal.model.request.jobvacancytransaction.CreateJobVacancyTransactionRequest;
import com.lawencon.jobportal.persistence.entity.JobVacancyTransaction;

public interface JobVacancyTransactionService {
    void create(CreateJobVacancyTransactionRequest request);

    Optional<JobVacancyTransaction> getLastByJobVacancyId(String jobVacancyId);
}
