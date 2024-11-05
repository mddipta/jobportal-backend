package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.lawencon.jobportal.model.request.CreateJobDescription;
import com.lawencon.jobportal.model.request.UpdateJobDescription;
import com.lawencon.jobportal.model.response.JobDescriptionResponse;
import com.lawencon.jobportal.persistence.entity.JobDescription;
import com.lawencon.jobportal.persistence.repository.JobDescriptionRepository;
import com.lawencon.jobportal.service.JobDescriptionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobDescriptionServiceImpl implements JobDescriptionService {
    private final JobDescriptionRepository repository;

    @Override
    public List<JobDescriptionResponse> getByJobTitle(String jobTitleId) {
        List<JobDescriptionResponse> responses = new ArrayList<>();
        List<JobDescription> jobDescriptions = repository.findByTitleJobId(jobTitleId);
        jobDescriptions.forEach(data -> responses.add(mapToResponse(data)));
        return responses;
    }

    @Override
    public void update(UpdateJobDescription request) {
        Optional<JobDescription> jobDescription = repository.findById(request.getId());
        if (jobDescription.isPresent()) {
            JobDescription jobDescriptionEntity = jobDescription.get();

            jobDescriptionEntity.setId(request.getId());
            jobDescriptionEntity.setDescription(request.getDescription());
            jobDescriptionEntity.setVersion(jobDescription.get().getVersion() + 1);

            repository.saveAndFlush(jobDescriptionEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job Description not found");
        }
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public void createSingle(CreateJobDescription request) {
        JobDescription jobDescription = new JobDescription();

        jobDescription.setTitleJob(request.getJobTitle());
        jobDescription.setDescription(request.getDescription());

        repository.save(jobDescription);
    }

    @Override
    public void createMultiple(List<CreateJobDescription> request) {
        List<JobDescription> jobDescriptions = new ArrayList<>();

        request.forEach(desc -> {
            JobDescription jobDescription = new JobDescription();

            jobDescription.setTitleJob(desc.getJobTitle());
            jobDescription.setDescription(desc.getDescription());

            jobDescriptions.add(jobDescription);
        });

        repository.saveAll(jobDescriptions);
    }

    @Override
    public void deleteMultiple(String titleJobId) {
        repository.deleteByTitleJobId(titleJobId);
    }

    @Override
    public JobDescriptionResponse getById(String id) {
        Optional<JobDescription> jobDescription = repository.findById(id);
        if (jobDescription.isPresent()) {
            return mapToResponse(jobDescription.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job Description not found");
        }
    }

    private JobDescriptionResponse mapToResponse(JobDescription jobDescription) {
        JobDescriptionResponse response = new JobDescriptionResponse();
        response.setTitleId(jobDescription.getTitleJob().getTitle());
        BeanUtils.copyProperties(jobDescription, response);
        return response;
    }



}
