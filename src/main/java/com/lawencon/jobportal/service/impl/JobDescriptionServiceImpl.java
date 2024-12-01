package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.lawencon.jobportal.helper.SpecificationHelper;
import com.lawencon.jobportal.model.request.CreateJobDescription;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.UpdateJobDescription;
import com.lawencon.jobportal.model.response.JobDescriptionResponse;
import com.lawencon.jobportal.persistence.entity.JobDescription;
import com.lawencon.jobportal.persistence.entity.JobTitle;
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
    public Page<JobDescriptionResponse> jobDescListPage(PagingRequest pagingRequest, String inquiry,
            String titleJobId) {
        PageRequest pageRequest =
                PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize(),
                        SpecificationHelper.createSort(pagingRequest.getSortBy()));

        Specification<JobDescription> spec = Specification.where(null);
        if (inquiry != null) {
            spec = spec.and(SpecificationHelper.inquiryFilter(List.of("description"), inquiry));
        }

        Page<JobDescription> jobDescriptionPage = repository.findAll(spec, pageRequest);

        List<JobDescriptionResponse> responses =
                jobDescriptionPage.getContent().stream().filter(data -> {
                    return data.getTitleJob().getId().equals(titleJobId);
                }).map(data -> mapToResponse(data)).toList();

        return new PageImpl<>(responses, pageRequest, jobDescriptionPage.getTotalElements());
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

        JobTitle jobTitle = request.getJobTitle();
        if (jobTitle.getVersion() == null) {
            jobTitle.setVersion((long) 0);
        }

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

    @Override
    public JobDescriptionResponse edit(String id) {
        Optional<JobDescription> jobDescription = repository.findById(id);
        if (jobDescription.isPresent()) {
            return mapToResponse(jobDescription.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job Description not found");
        }
    }



}
