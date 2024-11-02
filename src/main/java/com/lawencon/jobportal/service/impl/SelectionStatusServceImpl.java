package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.lawencon.jobportal.model.response.selectionstatus.SelectionStatusResponse;
import com.lawencon.jobportal.persistence.entity.SelectionStageStatus;
import com.lawencon.jobportal.persistence.repository.SelectionStatusRepository;
import com.lawencon.jobportal.service.SelectionStatusService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SelectionStatusServceImpl implements SelectionStatusService {
    SelectionStatusRepository repository;

    @Override
    public List<SelectionStatusResponse> getAll() {
        List<SelectionStatusResponse> responses = new ArrayList<>();
        List<SelectionStageStatus> selectionStageStatuses = repository.findAll();
        selectionStageStatuses.forEach(data -> {
            responses.add(mapToResponse(data));
        });
        return responses;
    }

    @Override
    public Optional<SelectionStageStatus> getEntityById(String id) {
        return repository.findById(id);
    }


    @Override
    public Optional<SelectionStatusResponse> getByCode(String code) {
        Optional<SelectionStageStatus> selectionStageStatus = repository.findByCode(code);
        if (selectionStageStatus.isPresent()) {
            SelectionStatusResponse response = new SelectionStatusResponse();
            BeanUtils.copyProperties(selectionStageStatus.get(), response);
            return Optional.of(response);
        } else {
            return Optional.empty();
        }
    }


    private SelectionStatusResponse mapToResponse(SelectionStageStatus selectionStageStatus) {
        SelectionStatusResponse response = new SelectionStatusResponse();
        BeanUtils.copyProperties(selectionStageStatus, response);
        return response;
    }
}
