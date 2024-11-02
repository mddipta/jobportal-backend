package com.lawencon.jobportal.service;

import java.util.List;

import com.lawencon.jobportal.model.request.education.CreateEducationRequest;
import com.lawencon.jobportal.model.request.education.UpdateEducationRequest;
import com.lawencon.jobportal.model.response.education.EducationResponse;

public interface EducationService {
    List<EducationResponse> getByUser();

    void create(CreateEducationRequest request);

    void update(UpdateEducationRequest request);

    void delete(String id);

    EducationResponse getById(String id);
}
