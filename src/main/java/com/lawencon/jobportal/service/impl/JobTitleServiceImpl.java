package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.lawencon.jobportal.helper.SpecificationHelper;
import com.lawencon.jobportal.helper.ValidateForeignKey;
import com.lawencon.jobportal.model.request.CreateJobDescription;
import com.lawencon.jobportal.model.request.CreateJobSpecification;
import com.lawencon.jobportal.model.request.CreateJobTitle;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.UpdateJobTitle;
import com.lawencon.jobportal.model.response.JobDescriptionResponse;
import com.lawencon.jobportal.model.response.JobSpecificationResponse;
import com.lawencon.jobportal.model.response.JobTitleDetailResponse;
import com.lawencon.jobportal.model.response.JobTitleResponse;
import com.lawencon.jobportal.persistence.entity.JobTitle;
import com.lawencon.jobportal.persistence.entity.JobVacancy;
import com.lawencon.jobportal.persistence.repository.JobTitleRepository;
import com.lawencon.jobportal.service.JobDescriptionService;
import com.lawencon.jobportal.service.JobSpecificationService;
import com.lawencon.jobportal.service.JobTitleService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobTitleServiceImpl implements JobTitleService {
    private final ValidateForeignKey validateForeignKey;

    private final JobTitleRepository repository;
    private final JobDescriptionService jobDescriptionService;
    private final JobSpecificationService jobSpecificationService;

    @Override
    public Page<JobTitleResponse> getAll(PagingRequest pagingRequest, String inquiry) {
        PageRequest pageRequest =
                PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize(),
                        SpecificationHelper.createSort(pagingRequest.getSortBy()));
        Specification<JobTitle> spec = Specification.where(null);
        if (inquiry != null) {
            spec = spec.and(SpecificationHelper.inquiryFilter(Arrays.asList("title"), inquiry));
        }

        Page<JobTitle> jobTitleResponse = repository.findAll(spec, pageRequest);

        List<JobTitleResponse> responses = jobTitleResponse.getContent().stream().map(jobTitle -> {
            JobTitleResponse response = mapToResponse(jobTitle);
            return response;
        }).toList();

        return new PageImpl<>(responses, pageRequest, jobTitleResponse.getTotalElements());
    }

    @Override
    public JobTitleDetailResponse getDetail(String id) {
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

        Boolean isHaveFk =
                validateForeignKey.isParentIdReferenced(id, JobVacancy.class, "jobTitle.id");
        if (isHaveFk) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "job title is being used");
        }


        jobDescriptionService.deleteMultiple(id);
        jobSpecificationService.deleteMultiple(id);

        repository.deleteById(id);
    }

    @Override
    public Long countJobTitle() {
        return repository.countBy();
    }

    @Override
    public JobTitleResponse getById(String id) {
        Optional<JobTitle> jobTitle = repository.findById(id);
        if (jobTitle.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "job title does not exist");
        }
        return mapToResponse(jobTitle.get());
    }

    private JobTitleResponse mapToResponse(JobTitle jobTitle) {
        JobTitleResponse response = new JobTitleResponse();
        BeanUtils.copyProperties(jobTitle, response);
        return response;
    }
}
