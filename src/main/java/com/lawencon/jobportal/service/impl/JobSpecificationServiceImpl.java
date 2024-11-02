package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.jobspecification.CreateJobSpecification;
import com.lawencon.jobportal.model.request.jobspecification.UpdateJobSpecification;
import com.lawencon.jobportal.model.response.jobspecification.JobSpecificationResponse;
import com.lawencon.jobportal.persistence.entity.JobSpecification;
import com.lawencon.jobportal.persistence.entity.JobTitle;
import com.lawencon.jobportal.persistence.repository.JobSpecificationRepository;
import com.lawencon.jobportal.persistence.repository.JobTitleRepository;
import com.lawencon.jobportal.service.JobSpecificationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobSpecificationServiceImpl implements JobSpecificationService {

    JobSpecificationRepository repository;
    JobTitleRepository jobTitleRepository;

    @Override
    public List<JobSpecificationResponse> getByJobTitle(String jobTitleId) {
        List<JobSpecificationResponse> responses = new ArrayList<>();
        List<JobSpecification> jobSpecifications = repository.findByJobTitleId(jobTitleId);
        jobSpecifications.forEach(data -> responses.add(mapToResponse(data)));
        return responses;
    }

    @Override
    public void update(UpdateJobSpecification request) {
        Optional<JobSpecification> jobSpecification = repository.findById(request.getId());

        if (jobSpecification.isPresent()) {
            JobSpecification jobSpecificationEntity = jobSpecification.get();

            jobSpecificationEntity.setId(request.getId());
            jobSpecificationEntity.setSpesification(request.getSpecification());

            repository.saveAndFlush(jobSpecificationEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Job Specification not found");
        }
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public void createSingle(CreateJobSpecification request) {
        JobSpecification jobSpecification = new JobSpecification();

        Optional<JobTitle> jobTitle = jobTitleRepository.findById(request.getJobTitle().getId());

        if (jobTitle.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job Title not found");
        }

        jobSpecification.setJobTitle(jobTitle.get());
        jobSpecification.setSpesification(request.getSpecification());

        repository.save(jobSpecification);
    }

    @Override
    public void createMultiple(List<CreateJobSpecification> request) {
        List<JobSpecification> jobSpecifications = new ArrayList<>();
        request.forEach(data -> {
            JobSpecification jobSpecification = new JobSpecification();

            jobSpecification.setJobTitle(data.getJobTitle());
            jobSpecification.setSpesification(data.getSpecification());

            jobSpecifications.add(jobSpecification);
        });
        repository.saveAll(jobSpecifications);
    }

    @Override
    public void deleteMultiple(String titleJobId) {
        repository.deleteByJobTitle_Id(titleJobId);
    }

    private JobSpecificationResponse mapToResponse(JobSpecification jobSpecification) {
        JobSpecificationResponse response = new JobSpecificationResponse();
        response.setTitleId(jobSpecification.getJobTitle().getId());
        BeanUtils.copyProperties(jobSpecification, response);
        return response;
    }

}
