package com.lawencon.jobportal.service;

import java.util.List;
import com.lawencon.jobportal.model.request.JobVacancyDetailRequest;
import com.lawencon.jobportal.model.response.JobVacancyDetailResponse;

public interface JobVacancyDetailService {
    void create(JobVacancyDetailRequest request);

    void update(JobVacancyDetailRequest request);

    List<JobVacancyDetailResponse> getByJobVacancyId(String id);

    List<JobVacancyDetailResponse> getByPicUserId(String id);

    void deleteByJobVacancyId(String id);

    void deleteByUserId(String id);
}
