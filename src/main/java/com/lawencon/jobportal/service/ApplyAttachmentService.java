package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.CreateApplyAttachmentRequest;
import com.lawencon.jobportal.model.response.ApplyAttachmentResponse;

import java.util.List;

public interface ApplyAttachmentService {
    void create(CreateApplyAttachmentRequest request);

    List<ApplyAttachmentResponse> getByApplyId(String applyId);
}
