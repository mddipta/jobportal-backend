package com.lawencon.jobportal.service;

import java.util.List;

import com.lawencon.jobportal.model.request.jobvacancy.CreateJobVacancyRequest;
import com.lawencon.jobportal.model.request.jobvacancy.SetPicToVacancyRequest;
import com.lawencon.jobportal.model.request.jobvacancy.UpdateJobVacancyRequest;
import com.lawencon.jobportal.model.request.jobvacancy.UpdateStatusJobVacancyRequest;
import com.lawencon.jobportal.model.response.jobvacancy.JobVacancyResponse;
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
}
