package com.lawencon.jobportal.service;

import java.util.List;
import com.lawencon.jobportal.model.request.CreateJobDescription;
import com.lawencon.jobportal.model.request.UpdateJobDescription;
import com.lawencon.jobportal.model.response.JobDescriptionResponse;

public interface JobDescriptionService {
    List<JobDescriptionResponse> getByJobTitle(String jobTitleId);

    void createSingle(CreateJobDescription request);

    void createMultiple(List<CreateJobDescription> request);

    void update(UpdateJobDescription request);

    void delete(String id);

    void deleteMultiple(String titleJobId);

    JobDescriptionResponse getById(String id);
}
