package com.lawencon.jobportal.service;

import java.util.List;
import com.lawencon.jobportal.model.request.CreateUserExperienceRequest;
import com.lawencon.jobportal.model.request.UpdateUserExperienceRequest;
import com.lawencon.jobportal.model.response.UserExperienceResponse;

public interface UserExperienceService {
    List<UserExperienceResponse> getByUserId();

    void create(CreateUserExperienceRequest request);

    void update(UpdateUserExperienceRequest request);

    void delete(String id);

    UserExperienceResponse getById(String id);
}
