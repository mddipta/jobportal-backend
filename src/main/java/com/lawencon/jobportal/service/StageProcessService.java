package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.CreateApplyStageProcessRequest;
import com.lawencon.jobportal.model.response.CandidateStageProcessResponse;
import com.lawencon.jobportal.model.response.ProcessSelectionStageResponse;
import com.lawencon.jobportal.model.response.StageProcessResponse;
import java.util.List;

public interface StageProcessService {
    void createStageProcessApply(CreateApplyStageProcessRequest request);

    List<CandidateStageProcessResponse> getByJobVacancyIdAndUserId(String jobVacancyId,
            String userId);

    List<ProcessSelectionStageResponse> getTopSelection();
}
