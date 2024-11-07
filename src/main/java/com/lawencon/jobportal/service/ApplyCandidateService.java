package com.lawencon.jobportal.service;

import java.util.List;
import com.lawencon.jobportal.model.request.CreateApplyCandidateRequest;
import com.lawencon.jobportal.model.response.ApplyCandidateResponse;

public interface ApplyCandidateService {
    List<ApplyCandidateResponse> getByUserIdLogin();

    List<ApplyCandidateResponse> getByUserId(String userId);

    void create(CreateApplyCandidateRequest request);
}
