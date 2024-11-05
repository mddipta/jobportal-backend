package com.lawencon.jobportal.service;

import java.util.List;
import com.lawencon.jobportal.model.request.CreateUserCertificationRequest;
import com.lawencon.jobportal.model.request.UpdateUserCertifiationRequest;
import com.lawencon.jobportal.model.response.UserCertificationResponse;

public interface UserCertificationService {
    List<UserCertificationResponse> getByUserId();

    void create(CreateUserCertificationRequest request);

    void update(UpdateUserCertifiationRequest request);

    UserCertificationResponse getById(String id);

    void delete(String id);
}
