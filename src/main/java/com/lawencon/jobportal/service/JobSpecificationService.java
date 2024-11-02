package com.lawencon.jobportal.service;

import java.util.List;

import com.lawencon.jobportal.model.request.jobspecification.CreateJobSpecification;
import com.lawencon.jobportal.model.request.jobspecification.UpdateJobSpecification;
import com.lawencon.jobportal.model.response.jobspecification.JobSpecificationResponse;

public interface JobSpecificationService {
    List<JobSpecificationResponse> getByJobTitle(String jobTitleId);

    void createSingle(CreateJobSpecification request);

    void createMultiple(List<CreateJobSpecification> request);

    void update(UpdateJobSpecification request);

    void delete(String id);

    void deleteMultiple(String titleJobId);
}
