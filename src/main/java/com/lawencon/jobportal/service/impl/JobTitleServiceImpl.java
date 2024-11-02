package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.jobdescription.CreateJobDescription;
import com.lawencon.jobportal.model.request.jobspecification.CreateJobSpecification;
import com.lawencon.jobportal.model.request.jobtitle.CreateJobTitle;
import com.lawencon.jobportal.model.request.jobtitle.UpdateJobTitle;
import com.lawencon.jobportal.model.response.jobdescription.JobDescriptionResponse;
import com.lawencon.jobportal.model.response.jobspecification.JobSpecificationResponse;
import com.lawencon.jobportal.model.response.jobtitle.JobTitleDetailResponse;
import com.lawencon.jobportal.model.response.jobtitle.JobTitleResponse;
import com.lawencon.jobportal.persistence.entity.JobTitle;
import com.lawencon.jobportal.persistence.repository.JobTitleRepository;
import com.lawencon.jobportal.service.JobDescriptionService;
import com.lawencon.jobportal.service.JobSpecificationService;
import com.lawencon.jobportal.service.JobTitleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobTitleServiceImpl implements JobTitleService {
    JobTitleRepository repository;
    JobDescriptionService jobDescriptionService;
    JobSpecificationService jobSpecificationService;

    @Override
    public List<JobTitleResponse> getAll() {
        List<JobTitleResponse> responses = new ArrayList<>();
        List<JobTitle> jobTitles = repository.findAll();
        jobTitles.forEach(jobTitle -> responses.add(mapToResponse(jobTitle)));
        return responses;
    }

    @Override
    public JobTitleDetailResponse getById(String id) {
        Optional<JobTitle> jobTitle = repository.findById(id);

        JobTitleDetailResponse response = new JobTitleDetailResponse();

        if (jobTitle.isPresent()) {
            response.setId(jobTitle.get().getId());
            response.setTitle(jobTitle.get().getTitle());

            List<JobDescriptionResponse> jobDescriptions = jobDescriptionService.getByJobTitle(id);
            response.setJobDescriptions(jobDescriptions);

            List<JobSpecificationResponse> jobSpecifications =
                    jobSpecificationService.getByJobTitle(id);
            response.setJobSpecifications(jobSpecifications);
            return response;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "job title does not exist");
    }

    @Override
    public Optional<JobTitle> getEntityById(String id) {
        return repository.findById(id);
    }

    @Override
    public void create(CreateJobTitle request) {
        List<CreateJobSpecification> jobSpecificationReq = new ArrayList<>();
        List<CreateJobDescription> jobDescriptionReq = new ArrayList<>();
        JobTitle jobTitle = new JobTitle();

        jobTitle.setTitle(request.getTitle());
        jobTitle.setIsActive(true);

        JobTitle savedJobTitle = repository.save(jobTitle);

        request.getJobDescription().forEach(desc -> {
            CreateJobDescription jobDesc = new CreateJobDescription();
            jobDesc.setJobTitle(savedJobTitle);
            jobDesc.setDescription(desc);
            jobDescriptionReq.add(jobDesc);
        });

        request.getJobSpecification().forEach(spec -> {
            CreateJobSpecification jobSpec = new CreateJobSpecification();
            jobSpec.setJobTitle(savedJobTitle);
            jobSpec.setSpecification(spec);
            jobSpecificationReq.add(jobSpec);
        });

        jobDescriptionService.createMultiple(jobDescriptionReq);
        jobSpecificationService.createMultiple(jobSpecificationReq);
    }

    @Override
    public void update(UpdateJobTitle request) {
        Optional<JobTitle> jobTitle = repository.findById(request.getId());
        if (jobTitle.isPresent()) {
            JobTitle jobTitleEntity = jobTitle.get();

            jobTitleEntity.setIsActive(request.getIsActive());
            jobTitleEntity.setTitle(request.getTitle());
            jobTitleEntity.setVersion(jobTitleEntity.getVersion() + 1);
            repository.saveAndFlush(jobTitleEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "job title does not exist");
        }
    }


    @Override
    @Transactional
    public void delete(String id) {
        jobDescriptionService.deleteMultiple(id);
        jobSpecificationService.deleteMultiple(id);

        repository.deleteById(id);
    }

    private JobTitleResponse mapToResponse(JobTitle jobTitle) {
        JobTitleResponse response = new JobTitleResponse();
        BeanUtils.copyProperties(jobTitle, response);
        return response;
    }

}
