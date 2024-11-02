package com.lawencon.jobportal.service;

import java.util.List;
import java.util.Optional;

import com.lawencon.jobportal.model.response.selectionstatus.SelectionStatusResponse;
import com.lawencon.jobportal.persistence.entity.SelectionStageStatus;

public interface SelectionStatusService {
    List<SelectionStatusResponse> getAll();

    Optional<SelectionStageStatus> getEntityById(String id);

    Optional<SelectionStatusResponse> getByCode(String code);
}
