package com.lawencon.jobportal.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.lawencon.jobportal.model.response.UserProfileResponse;
import com.lawencon.jobportal.service.UserProfileService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.lawencon.jobportal.authentication.helper.SessionHelper;
import com.lawencon.jobportal.model.request.CreateApplyAttachmentRequest;
import com.lawencon.jobportal.model.request.CreateApplyCandidateRequest;
import com.lawencon.jobportal.model.response.ApplyAttachmentResponse;
import com.lawencon.jobportal.model.response.ApplyCandidateResponse;
import com.lawencon.jobportal.persistence.entity.ApplyCandidate;
import com.lawencon.jobportal.persistence.entity.JobVacancy;
import com.lawencon.jobportal.persistence.entity.User;
import com.lawencon.jobportal.persistence.repository.ApplyCandidateRepository;
import com.lawencon.jobportal.service.ApplyAttachmentService;
import com.lawencon.jobportal.service.ApplyCandidateService;
import com.lawencon.jobportal.service.JobVacancyService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApplyCandidateServiceImpl implements ApplyCandidateService {
    private final ApplyCandidateRepository repository;
    private final JobVacancyService jobVacancyService;
    private final ApplyAttachmentService applyAttachmentService;
    private final UserProfileService userProfileService;

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
        LocalDate now = LocalDate.now();
        UserProfileResponse userProfile = userProfileService.getByUserId(user.getId());
        Optional<ApplyCandidate> applyCandidateExist =
                repository.findByJobVacancy_Id(request.getVacancyId());


        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

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
    }

    private ApplyCandidateResponse mapToResponse(ApplyCandidate applyCandidate) {
        ApplyCandidateResponse response = new ApplyCandidateResponse();
        List<String> fileUrls = new ArrayList<>();

        response.setVacancyName(applyCandidate.getJobVacancy().getJobTitle().getTitle());
        response.setVacancyId(applyCandidate.getJobVacancy().getId());
        response.setDateApply(applyCandidate.getDateApply().toString());

        List<ApplyAttachmentResponse> files =
                applyAttachmentService.getByApplyId(applyCandidate.getId());
        files.forEach(file -> {
            String fileUrl = file.getAttachment();
            fileUrls.add(fileUrl);
        });

        response.setAttachments(fileUrls);

        BeanUtils.copyProperties(applyCandidate, response);
        return response;
    }
}
