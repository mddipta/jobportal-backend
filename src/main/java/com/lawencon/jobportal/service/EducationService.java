package com.lawencon.jobportal.service;

import java.util.List;
import com.lawencon.jobportal.model.request.CreateEducationRequest;
import com.lawencon.jobportal.model.request.UpdateEducationRequest;
import com.lawencon.jobportal.model.response.EducationResponse;

public interface EducationService {
    List<EducationResponse> getByUser();

    void create(CreateEducationRequest request);

    void update(UpdateEducationRequest request);

    void delete(String id);

    EducationResponse getById(String id);
}
