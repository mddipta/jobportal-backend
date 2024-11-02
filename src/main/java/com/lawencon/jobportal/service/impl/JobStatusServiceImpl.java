package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.model.response.jobstatus.JobStatusResponse;
import com.lawencon.jobportal.persistence.entity.JobStatus;
import com.lawencon.jobportal.persistence.repository.JobStatusRepository;
import com.lawencon.jobportal.service.JobStatusService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobStatusServiceImpl implements JobStatusService {
    private final JobStatusRepository repository;

    @Override
    public List<JobStatusResponse> getAll() {

        List<JobStatusResponse> responses = new ArrayList<>();
        List<JobStatus> jobStatuses = repository.findAll();
        jobStatuses.forEach(data -> responses.add(mapToResponse(data)));
        return responses;
    }

    @Override
    public JobStatus getEntityById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public JobStatusResponse getByCode(String code) {
        JobStatus jobStatus = repository.findByCode(code).orElse(null);
        if (jobStatus == null) {
            return null;
        }
        return mapToResponse(jobStatus);
    }


    private JobStatusResponse mapToResponse(JobStatus jobStatus) {
        JobStatusResponse response = new JobStatusResponse();
        BeanUtils.copyProperties(jobStatus, response);
        return response;
    }
}
