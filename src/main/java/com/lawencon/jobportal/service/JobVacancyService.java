package com.lawencon.jobportal.service;

import java.util.List;
import com.lawencon.jobportal.model.request.CreateJobVacancyRequest;
import com.lawencon.jobportal.model.request.SetPicToVacancyRequest;
import com.lawencon.jobportal.model.request.UpdateJobVacancyRequest;
import com.lawencon.jobportal.model.request.UpdatePicJobVacancyRequest;
import com.lawencon.jobportal.model.request.UpdateStatusJobVacancyRequest;
import com.lawencon.jobportal.model.response.JobVacancyResponse;
import com.lawencon.jobportal.persistence.entity.JobVacancy;

public interface JobVacancyService {
    List<JobVacancyResponse> getAll();

    JobVacancyResponse getById(String id);

    JobVacancy getEntityById(String id);

    void create(CreateJobVacancyRequest request);

    void update(UpdateJobVacancyRequest request);

    void delete(String id);

    void setPicToVacancy(SetPicToVacancyRequest request);

    void publishVacancy(UpdateStatusJobVacancyRequest request);

    void updatePicToVacancy(UpdatePicJobVacancyRequest request);
}
