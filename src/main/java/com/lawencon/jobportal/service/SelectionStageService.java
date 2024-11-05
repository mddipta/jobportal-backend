package com.lawencon.jobportal.service;

import java.util.List;
import com.lawencon.jobportal.model.response.SelectionStageResponse;
import com.lawencon.jobportal.persistence.entity.SelectionStage;

public interface SelectionStageService {
    List<SelectionStageResponse> getAll();

    SelectionStage getEntityById(String id);

    SelectionStageResponse getByCode(String code);
}
