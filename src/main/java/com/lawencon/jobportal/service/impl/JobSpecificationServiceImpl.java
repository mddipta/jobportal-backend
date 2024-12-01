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
import com.lawencon.jobportal.model.request.CreateJobSpecification;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.UpdateJobSpecification;
import com.lawencon.jobportal.model.response.JobSpecificationResponse;
import com.lawencon.jobportal.persistence.entity.JobSpecification;
import com.lawencon.jobportal.persistence.entity.JobTitle;
import com.lawencon.jobportal.persistence.repository.JobSpecificationRepository;
import com.lawencon.jobportal.service.JobSpecificationService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobSpecificationServiceImpl implements JobSpecificationService {

    private final JobSpecificationRepository repository;

    @Override
    public JobSpecificationResponse edit(String id) {
        Optional<JobSpecification> jobSpecification = repository.findById(id);
        if (jobSpecification.isPresent()) {
            return mapToResponse(jobSpecification.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Job Specification not found");
        }
    }

    @Override
    public List<JobSpecificationResponse> getByJobTitle(String jobTitleId) {
        List<JobSpecificationResponse> responses = new ArrayList<>();
        List<JobSpecification> jobSpecifications = repository.findByJobTitleId(jobTitleId);
        jobSpecifications.forEach(data -> responses.add(mapToResponse(data)));
        return responses;
    }

    @Override
    public Page<JobSpecificationResponse> jobSpecListPage(PagingRequest pagingRequest,
            String inquiry, String titleJobId) {
        PageRequest pageRequest =
                PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize(),
                        SpecificationHelper.createSort(pagingRequest.getSortBy()));

        Specification<JobSpecification> spec = Specification.where(null);
        if (inquiry != null) {
            spec = spec.and(SpecificationHelper.inquiryFilter(List.of("specification"), inquiry));
        }

        Page<JobSpecification> jobSpecificationPage = repository.findAll(spec, pageRequest);

        List<JobSpecificationResponse> responses =
                jobSpecificationPage.getContent().stream().filter(data -> {
                    return data.getJobTitle().getId().equals(titleJobId);
                }).map(data -> mapToResponse(data)).toList();

        return new PageImpl<>(responses, pageRequest, jobSpecificationPage.getTotalElements());
    }


    @Override
    public void update(UpdateJobSpecification request) {
        Optional<JobSpecification> jobSpecification = repository.findById(request.getId());

        if (jobSpecification.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Job Specification not found");
        }

        JobSpecification jobSpecificationEntity = jobSpecification.get();

        jobSpecificationEntity.setId(request.getId());
        jobSpecificationEntity.setSpesification(request.getSpecification());

        repository.saveAndFlush(jobSpecificationEntity);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public void createSingle(CreateJobSpecification request) {
        JobSpecification jobSpecification = new JobSpecification();

        JobTitle jobTitle = request.getJobTitle();
        if (jobTitle.getVersion() == null) {
            jobTitle.setVersion((long) 0);
        }

        jobSpecification.setJobTitle(request.getJobTitle());
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

    @Override
    public JobSpecificationResponse getById(String id) {
        Optional<JobSpecification> jobSpecification = repository.findById(id);
        if (jobSpecification.isPresent()) {
            return mapToResponse(jobSpecification.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Job Specification not found");
        }
    }

    private JobSpecificationResponse mapToResponse(JobSpecification jobSpecification) {
        JobSpecificationResponse response = new JobSpecificationResponse();
        response.setSpecification(jobSpecification.getSpesification());
        response.setTitleId(jobSpecification.getJobTitle().getId());
        BeanUtils.copyProperties(jobSpecification, response);
        return response;
    }



}
