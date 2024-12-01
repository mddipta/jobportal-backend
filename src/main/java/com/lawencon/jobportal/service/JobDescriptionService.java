package com.lawencon.jobportal.service;

import java.util.List;
import org.springframework.data.domain.Page;
import com.lawencon.jobportal.model.request.CreateJobDescription;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.UpdateJobDescription;
import com.lawencon.jobportal.model.response.JobDescriptionResponse;

public interface JobDescriptionService {
    List<JobDescriptionResponse> getByJobTitle(String jobTitleId);

    Page<JobDescriptionResponse> jobDescListPage(PagingRequest pagingRequest, String inquiry,
            String titleJobId);

    void createSingle(CreateJobDescription request);

    void createMultiple(List<CreateJobDescription> request);

    JobDescriptionResponse edit(String id);

    void update(UpdateJobDescription request);

    void delete(String id);

    void deleteMultiple(String titleJobId);

    JobDescriptionResponse getById(String id);
}
