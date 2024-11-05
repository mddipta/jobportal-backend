package com.lawencon.jobportal.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.lawencon.jobportal.model.request.CreateJobVacancyTransactionRequest;
import com.lawencon.jobportal.persistence.entity.JobVacancyTransaction;
import com.lawencon.jobportal.persistence.repository.JobVacancyTransactionRepository;
import com.lawencon.jobportal.service.JobVacancyTransactionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobVacancyTransactionServiceImpl implements JobVacancyTransactionService {
    private final JobVacancyTransactionRepository repository;

    @Override
    public void create(CreateJobVacancyTransactionRequest request) {
        Optional<JobVacancyTransaction> jobVacancyTransaction = repository
                .findTopByJobVacancyIdOrderByCreatedAtDesc(request.getJobVacancy().getId());

        JobVacancyTransaction entity = new JobVacancyTransaction();
        if (jobVacancyTransaction.isPresent()) {
            entity.setNumber(jobVacancyTransaction.get().getNumber() + 1);
        } else {
            entity.setNumber(1L);
        }

        entity.setJobVacancy(request.getJobVacancy());
        entity.setJobStatus(request.getJobStatus());
        entity.setUser(request.getUser());
        entity.setDate(request.getDate());
        repository.save(entity);
    }

    @Override
    public Optional<JobVacancyTransaction> getLastByJobVacancyId(String jobVacancyId) {
        return repository.findTopByJobVacancyIdOrderByCreatedAtDesc(jobVacancyId);
    }



}
