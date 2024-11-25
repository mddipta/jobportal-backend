package com.lawencon.jobportal.service;

import java.util.List;
import com.lawencon.jobportal.model.request.CreateApplyCandidateRequest;
import com.lawencon.jobportal.model.response.ApplyCandidateResponse;
import com.lawencon.jobportal.model.response.ApplyCandidateResponseDetail;

public interface ApplyCandidateService {
    List<ApplyCandidateResponse> getByUserIdLogin();

    List<ApplyCandidateResponse> getByUserId(String userId);

    void create(CreateApplyCandidateRequest request);

    ApplyCandidateResponseDetail detail(String id);
}
