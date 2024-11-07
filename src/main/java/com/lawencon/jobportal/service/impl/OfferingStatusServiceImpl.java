package com.lawencon.jobportal.service.impl;

import com.lawencon.jobportal.model.response.OfferingStatusResponse;
import com.lawencon.jobportal.persistence.entity.OfferingStatus;
import com.lawencon.jobportal.persistence.repository.OfferingStatusRepository;
import com.lawencon.jobportal.service.OfferingStatusService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OfferingStatusServiceImpl implements OfferingStatusService {
    private final OfferingStatusRepository repository;

    @Override
    public List<OfferingStatusResponse> getAll() {
        List<OfferingStatusResponse> responses = new ArrayList<>();
        List<OfferingStatus> data = repository.findAll();
        data.forEach(status -> responses.add(mapToResponse(status)));
        return responses;
    }

    @Override
    public OfferingStatusResponse getById(String id) {
        return mapToResponse(repository.findById(id).orElse(null));
    }

    @Override
    public OfferingStatusResponse getByCode(String code) {
        return mapToResponse(repository.findByCode(code).orElse(null));
    }

    @Override
    public OfferingStatus getEntityById(String id) {
        return repository.findById(id).orElse(null);
    }

    private OfferingStatusResponse mapToResponse(OfferingStatus status){
        OfferingStatusResponse response = new OfferingStatusResponse();
        BeanUtils.copyProperties(status, response);
        return response;
    }
}
