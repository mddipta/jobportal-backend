package com.lawencon.jobportal.service;

import java.util.List;
import com.lawencon.jobportal.model.request.CreateApplyStageProcessRequest;
import com.lawencon.jobportal.model.response.CandidateStageProcessResponse;
import com.lawencon.jobportal.model.response.ProcessSelectionStageResponse;

public interface StageProcessService {
    void createStageProcessApply(CreateApplyStageProcessRequest request);

    List<CandidateStageProcessResponse> getByJobVacancyIdAndUserId(String jobVacancyId,
            String userId);

    List<ProcessSelectionStageResponse> getTopSelection();
}
