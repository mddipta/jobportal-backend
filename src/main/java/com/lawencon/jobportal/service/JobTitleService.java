package com.lawencon.jobportal.service;

import java.util.List;
import java.util.Optional;

import com.lawencon.jobportal.model.request.jobtitle.CreateJobTitle;
import com.lawencon.jobportal.model.request.jobtitle.UpdateJobTitle;
import com.lawencon.jobportal.model.response.jobtitle.JobTitleDetailResponse;
import com.lawencon.jobportal.model.response.jobtitle.JobTitleResponse;
import com.lawencon.jobportal.persistence.entity.JobTitle;

public interface JobTitleService {
    List<JobTitleResponse> getAll();

    JobTitleDetailResponse getById(String id);

    Optional<JobTitle> getEntityById(String id);

    void create(CreateJobTitle request);

    void update(UpdateJobTitle request);

    void delete(String id);
}
