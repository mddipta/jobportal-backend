package com.lawencon.jobportal.service.impl;

import com.lawencon.jobportal.model.request.CreateApplyStageProcessRequest;
import com.lawencon.jobportal.model.response.CandidateStageProcessResponse;
import com.lawencon.jobportal.model.response.SelectionStatusResponse;
import com.lawencon.jobportal.persistence.entity.SelectionStageStatus;
import com.lawencon.jobportal.persistence.entity.StageProcess;
import com.lawencon.jobportal.persistence.repository.StageProcessRepository;
import com.lawencon.jobportal.service.SelectionStatusService;
import com.lawencon.jobportal.service.StageProcessService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StageProcessServiceImpl implements StageProcessService {
    private final StageProcessRepository repository;
    private final SelectionStatusService selectionStatusService;

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
        if(selectionStatus.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Selection status not found");
        }
        SelectionStageStatus selectionStageStatus = selectionStatusService.getEntityById(selectionStatus.get().getId()).get();
        stageProcess.setStatusSelection(selectionStageStatus);

        repository.saveAndFlush(stageProcess);
    }

    @Override
    public List<CandidateStageProcessResponse> getByJobVacancyId(String jobVacancyId) {
        List<CandidateStageProcessResponse> responses = new ArrayList<>();
        List<StageProcess> data = repository.findByVacancyJob_Id(jobVacancyId);
        data.forEach(stageProcess -> {
            CandidateStageProcessResponse response = new CandidateStageProcessResponse();
            response.setId(stageProcess.getId());
            response.setStage(stageProcess.getStageSelection().getName());
            response.setStatus(stageProcess.getStatusSelection().getName());
            responses.add(response);
        });

        return responses;
    }
}
