package com.lawencon.jobportal.service;

import java.util.List;
import org.springframework.data.domain.Page;
import com.lawencon.jobportal.model.request.CreateJobSpecification;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.UpdateJobSpecification;
import com.lawencon.jobportal.model.response.JobSpecificationResponse;

public interface JobSpecificationService {
    List<JobSpecificationResponse> getByJobTitle(String jobTitleId);

    Page<JobSpecificationResponse> jobSpecListPage(PagingRequest pagingRequest, String inquiry,
            String titleJobId);

    void createSingle(CreateJobSpecification request);

    void createMultiple(List<CreateJobSpecification> request);

    JobSpecificationResponse edit(String id);

    void update(UpdateJobSpecification request);

    void delete(String id);

    void deleteMultiple(String titleJobId);

    JobSpecificationResponse getById(String id);
}
