package com.lawencon.jobportal.service;

import java.util.List;

import com.lawencon.jobportal.model.response.jobstatus.JobStatusResponse;
import com.lawencon.jobportal.persistence.entity.JobStatus;

public interface JobStatusService {
    List<JobStatusResponse> getAll();

    JobStatus getEntityById(String id);

    JobStatusResponse getByCode(String code);
}
