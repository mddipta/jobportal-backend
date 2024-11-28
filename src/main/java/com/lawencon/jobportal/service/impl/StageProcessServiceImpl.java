package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.lawencon.jobportal.model.request.CreateApplyStageProcessRequest;
import com.lawencon.jobportal.model.response.CandidateStageProcessResponse;
import com.lawencon.jobportal.model.response.ProcessSelectionStageResponse;
import com.lawencon.jobportal.model.response.SelectionStatusResponse;
import com.lawencon.jobportal.persistence.entity.SelectionStageStatus;
import com.lawencon.jobportal.persistence.entity.StageProcess;
import com.lawencon.jobportal.persistence.repository.StageProcessRepository;
import com.lawencon.jobportal.service.SelectionStatusService;
import com.lawencon.jobportal.service.StageProcessService;
import com.lawencon.jobportal.service.UserProfileService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StageProcessServiceImpl implements StageProcessService {
    private final StageProcessRepository repository;
    private final SelectionStatusService selectionStatusService;
    private final UserProfileService userProfileService;


    @Override
    public void createStageProcessApply(CreateApplyStageProcessRequest request) {
        StageProcess stageProcess = new StageProcess();
        stageProcess.setCandidateUser(request.getUser());
        stageProcess.setVacancyJob(request.getVacancy());
        stageProcess.setStageSelection(request.getStage());
        stageProcess.setStatusSelection(request.getStatus());
        stageProcess.setDate(request.getDate());
        stageProcess.setNumber(1L);

        Optional<SelectionStatusResponse> selectionStatus = selectionStatusService.getByCode("PDG");
        if (selectionStatus.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Selection status not found");
        }
        SelectionStageStatus selectionStageStatus =
                selectionStatusService.getEntityById(selectionStatus.get().getId()).get();
        stageProcess.setStatusSelection(selectionStageStatus);

        repository.saveAndFlush(stageProcess);
    }

    @Override
    public List<CandidateStageProcessResponse> getByJobVacancyIdAndUserId(String jobVacancyId,
            String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'getByJobVacancyIdAndUserId'");
    }

    @Override
    public List<ProcessSelectionStageResponse> getTopSelection() {
        List<StageProcess> data = repository.findTop5ByOrderByCreatedAtDesc();
        List<ProcessSelectionStageResponse> responses = new ArrayList<>();

        data.forEach(stageProcess -> {
            String userCandidateId = stageProcess.getCandidateUser().getId();
            String nameCandidate = userProfileService.getByUserId(userCandidateId).getName();

            ProcessSelectionStageResponse response = new ProcessSelectionStageResponse();
            response.setName(nameCandidate);
            response.setSelectionStage(stageProcess.getStageSelection().getName());
            response.setSelectionDate(stageProcess.getDate().toString());
            response.setVacancy(stageProcess.getVacancyJob().getJobTitle().getTitle());

            responses.add(response);
        });

        return responses;
    }



}
