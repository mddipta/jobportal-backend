package com.lawencon.jobportal.service.impl;

import com.lawencon.jobportal.model.request.CreateApplyAttachmentRequest;
import com.lawencon.jobportal.model.response.ApplyAttachmentResponse;
import com.lawencon.jobportal.persistence.entity.ApplyAttachment;
import com.lawencon.jobportal.persistence.entity.File;
import com.lawencon.jobportal.persistence.repository.ApplyAttachmentRepository;
import com.lawencon.jobportal.service.ApplyAttachmentService;
import com.lawencon.jobportal.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ApplyAttachmentServiceImpl implements ApplyAttachmentService {
    private final ApplyAttachmentRepository repository;

    private final FileService fileService;

    @Override
    public void create(CreateApplyAttachmentRequest request) {

        List<File> fileEntities = fileService.save(request.getAttachment(), request.getDocumentType());
        List<ApplyAttachment> entites = new ArrayList<>();

        fileEntities.forEach(file -> {
            ApplyAttachment entity = new ApplyAttachment();
            entity.setFile(file);
            entity.setApplyCandidate(request.getApplyCandidates());
            entites.add(entity);
        });
        repository.saveAll(entites);
    }

    @Override
    public List<ApplyAttachmentResponse> getByApplyId(String applyId) {
        List<ApplyAttachmentResponse> responses = new ArrayList<>();
        List<ApplyAttachment> applyAttachments = repository.findByApplyCandidate_Id(applyId);

        applyAttachments.forEach(applyAttachment -> {
            responses.add(mapToResponse(applyAttachment));
        });

        return responses;
    }

    private ApplyAttachmentResponse mapToResponse(ApplyAttachment applyAttachment){
        ApplyAttachmentResponse response = new ApplyAttachmentResponse();
        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploads/")
                .path(applyAttachment.getFile().getFile()).toUriString();
        response.setAttachment(fileUrl);
        response.setId(applyAttachment.getId());
        return response;
    }
}
