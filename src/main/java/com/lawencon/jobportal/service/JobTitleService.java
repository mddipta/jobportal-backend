package com.lawencon.jobportal.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import com.lawencon.jobportal.model.request.CreateJobTitle;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.UpdateJobTitle;
import com.lawencon.jobportal.model.response.JobTitleDetailResponse;
import com.lawencon.jobportal.model.response.JobTitleResponse;
import com.lawencon.jobportal.persistence.entity.JobTitle;

public interface JobTitleService {
    Page<JobTitleResponse> getAll(PagingRequest pagingRequest, String inquiry);

    JobTitleDetailResponse getDetail(String id);

    Optional<JobTitle> getEntityById(String id);

    JobTitleResponse getById(String id);

    void create(CreateJobTitle request);

    void update(UpdateJobTitle request);

    void delete(String id);

    Long countJobTitle();
}
