package com.lawencon.jobportal.service;

import java.util.List;
import com.lawencon.jobportal.model.request.CreateJobSpecification;
import com.lawencon.jobportal.model.request.UpdateJobSpecification;
import com.lawencon.jobportal.model.response.JobSpecificationResponse;

public interface JobSpecificationService {
    List<JobSpecificationResponse> getByJobTitle(String jobTitleId);

    void createSingle(CreateJobSpecification request);

    void createMultiple(List<CreateJobSpecification> request);

    void update(UpdateJobSpecification request);

    void delete(String id);

    void deleteMultiple(String titleJobId);

    JobSpecificationResponse getById(String id);
}
