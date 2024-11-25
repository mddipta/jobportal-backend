package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.CreateApplyStageProcessRequest;
import com.lawencon.jobportal.model.response.CandidateStageProcessResponse;

import java.util.List;

public interface StageProcessService {
    void createStageProcessApply(CreateApplyStageProcessRequest request);

    List<CandidateStageProcessResponse> getByJobVacancyId(String jobVacancyId);
}
