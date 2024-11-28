package com.lawencon.jobportal.service.impl;

import com.lawencon.jobportal.authentication.helper.SessionHelper;
import com.lawencon.jobportal.model.request.CreateApplyAttachmentRequest;
import com.lawencon.jobportal.model.request.CreateApplyCandidateRequest;
import com.lawencon.jobportal.model.request.CreateApplyStageProcessRequest;
import com.lawencon.jobportal.model.response.*;
import com.lawencon.jobportal.persistence.entity.ApplyCandidate;
import com.lawencon.jobportal.persistence.entity.JobVacancy;
import com.lawencon.jobportal.persistence.entity.SelectionStage;
import com.lawencon.jobportal.persistence.entity.User;
import com.lawencon.jobportal.persistence.repository.ApplyCandidateRepository;
import com.lawencon.jobportal.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplyCandidateServiceImpl implements ApplyCandidateService {
    private final ApplyCandidateRepository repository;
    private final JobVacancyService jobVacancyService;
    private final ApplyAttachmentService applyAttachmentService;
    private final UserProfileService userProfileService;
    private final StageProcessService stageProcessService;
    private final SelectionStageService selectionStageService;

    @Override
    public List<ApplyCandidateResponse> getByUserIdLogin() {
        User user = SessionHelper.getLoginUser();

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

        List<ApplyCandidateResponse> responses = new ArrayList<>();
        List<ApplyCandidate> applyCandidate = repository.findByUserId(user.getId());

        applyCandidate.forEach(apply -> responses.add(mapToResponse(apply)));
        return responses;
    }

    @Override
    public List<ApplyCandidateResponse> getByUserId(String userId) {
        List<ApplyCandidateResponse> responses = new ArrayList<>();
        List<ApplyCandidate> applyCandidate = repository.findByUserId(userId);

        applyCandidate.forEach(apply -> responses.add(mapToResponse(apply)));
        return responses;
    }

    @Override
    @Transactional
    public void create(CreateApplyCandidateRequest request) {
        User user = SessionHelper.getLoginUser();
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

        LocalDate now = LocalDate.now();
        UserProfileResponse userProfile = userProfileService.getByUserId(user.getId());
        Optional<ApplyCandidate> applyCandidateExist =
                repository.findByJobVacancy_Id(request.getVacancyId());

        if (userProfile == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CV must be uploaded");
        }

        if (applyCandidateExist.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "You have applied for this job vacancy");
        }

        ApplyCandidate applyCandidate = new ApplyCandidate();
        applyCandidate.setDateApply(now);
        applyCandidate.setUser(user);

        JobVacancy jobVacancy = jobVacancyService.getEntityById(request.getVacancyId());
        applyCandidate.setJobVacancy(jobVacancy);

        applyCandidate = repository.saveAndFlush(applyCandidate);

        CreateApplyAttachmentRequest attachmentRequest = new CreateApplyAttachmentRequest();
        attachmentRequest.setAttachment(request.getAttachments());
        attachmentRequest.setDocumentType(request.getDocumentTypes());
        attachmentRequest.setApplyCandidates(applyCandidate);

        applyAttachmentService.create(attachmentRequest);

        CreateApplyStageProcessRequest requestStage = new CreateApplyStageProcessRequest();
        requestStage.setUser(user);
        requestStage.setVacancy(jobVacancy);

        SelectionStageResponse stageResponse = selectionStageService.getByCode("APL");
        SelectionStage selectionStage = selectionStageService.getEntityById(stageResponse.getId());
        requestStage.setStage(selectionStage);

        requestStage.setDate(ZonedDateTime.now(ZoneOffset.UTC));
        stageProcessService.createStageProcessApply(requestStage);
    }

    @Override
    public ApplyCandidateResponseDetail detail(String id) {
        ApplyCandidateResponseDetail response = new ApplyCandidateResponseDetail();
        Optional<ApplyCandidate> applyCandidate = repository.findById(id);
        List<String> fileUrls = new ArrayList<>();

        if (applyCandidate.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Apply Candidate not found");
        }

        response.setId(applyCandidate.get().getId());
        response.setVacancyId(applyCandidate.get().getJobVacancy().getId());
        response.setVacancyName(applyCandidate.get().getJobVacancy().getJobTitle().getTitle());
        response.setDateApply(applyCandidate.get().getDateApply().toString());

        List<ApplyAttachmentResponse> files =
                applyAttachmentService.getByApplyId(applyCandidate.get().getId());
        files.forEach(file -> {
            String fileUrl = file.getAttachment();
            fileUrls.add(fileUrl);
        });

        response.setAttachments(fileUrls);
        List<CandidateStageProcessResponse> stageProcessSelection =
                stageProcessService.getByJobVacancyIdAndUserId(applyCandidate.get().getJobVacancy().getId(), applyCandidate.get().getUser().getId());
        response.setStageProcess(stageProcessSelection);

        return response;
    }

    private ApplyCandidateResponse mapToResponse(ApplyCandidate applyCandidate) {
        ApplyCandidateResponse response = new ApplyCandidateResponse();


        response.setVacancyName(applyCandidate.getJobVacancy().getJobTitle().getTitle());
        response.setVacancyId(applyCandidate.getJobVacancy().getId());
        response.setDateApply(applyCandidate.getDateApply().toString());


        BeanUtils.copyProperties(applyCandidate, response);
        return response;
    }
}
