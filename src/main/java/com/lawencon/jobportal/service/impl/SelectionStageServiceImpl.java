package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.model.response.selectionstages.SelectionStageResponse;
import com.lawencon.jobportal.persistence.entity.SelectionStage;
import com.lawencon.jobportal.persistence.repository.SelectionStagesRepository;
import com.lawencon.jobportal.service.SelectionStageService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SelectionStageServiceImpl implements SelectionStageService {
    SelectionStagesRepository repository;

    @Override
    public List<SelectionStageResponse> getAll() {
        List<SelectionStageResponse> responses = new ArrayList<>();
        List<SelectionStage> selectionStages = repository.findAll();
        selectionStages.forEach(data -> responses.add(mapToResponse(data)));
        return responses;
    }

    @Override
    public SelectionStage getEntityById(String id) {
        Optional<SelectionStage> selectionStage = repository.findById(id);
        if (selectionStage.isEmpty()) {
            return null;
        } else {
            return selectionStage.get();
        }
    }

    @Override
    public SelectionStageResponse getByCode(String code) {
        Optional<SelectionStage> selectionStage = repository.findByCode(code);
        if (selectionStage.isEmpty()) {
            return null;
        } else {
            return mapToResponse(selectionStage.get());
        }
    }

    private SelectionStageResponse mapToResponse(SelectionStage selectionStage) {
        SelectionStageResponse response = new SelectionStageResponse();
        BeanUtils.copyProperties(selectionStage, response);
        return response;
    }

}
