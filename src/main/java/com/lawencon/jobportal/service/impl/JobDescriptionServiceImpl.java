package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.model.request.jobdescription.CreateJobDescription;
import com.lawencon.jobportal.model.request.jobdescription.UpdateJobDescription;
import com.lawencon.jobportal.model.response.jobdescription.JobDescriptionResponse;
import com.lawencon.jobportal.persistence.entity.JobDescription;
import com.lawencon.jobportal.persistence.entity.JobTitle;
import com.lawencon.jobportal.persistence.repository.JobDescriptionRepository;
import com.lawencon.jobportal.persistence.repository.JobTitleRepository;
import com.lawencon.jobportal.service.JobDescriptionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobDescriptionServiceImpl implements JobDescriptionService {
    JobDescriptionRepository repository;

    JobTitleRepository jobTitleRepository;

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

        Optional<JobTitle> jobTitle = jobTitleRepository.findById(request.getJobTitle().getId());
        if (jobTitle.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job Title not found");
        }

        jobDescription.setTitleJob(jobTitle.get());
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

    private JobDescriptionResponse mapToResponse(JobDescription jobDescription) {
        JobDescriptionResponse response = new JobDescriptionResponse();
        response.setTitleId(jobDescription.getTitleJob().getTitle());
        BeanUtils.copyProperties(jobDescription, response);
        return response;
    }

    @Override
    public void deleteMultiple(String titleJobId) {
        repository.deleteByTitleJobId(titleJobId);
    }

}
