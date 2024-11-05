package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.lawencon.jobportal.model.response.EmploymentTypeResponse;
import com.lawencon.jobportal.persistence.entity.EmploymentType;
import com.lawencon.jobportal.persistence.repository.EmploymentTypeRepository;
import com.lawencon.jobportal.service.EmploymentTypeService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmploymentTypeServiceImpl implements EmploymentTypeService {
    private final EmploymentTypeRepository repository;

    @Override
    public List<EmploymentTypeResponse> getAll() {
        List<EmploymentTypeResponse> responses = new ArrayList<>();
        List<EmploymentType> employmentTypes = repository.findAll();
        employmentTypes.forEach(data -> responses.add(mapToResponse(data)));
        return responses;
    }

    @Override
    public Optional<EmploymentType> getEntityById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<EmploymentTypeResponse> getByCode(String code) {
        Optional<EmploymentType> employmentType = repository.findByCode(code);
        if (employmentType == null) {
            return Optional.empty();
        }
        return employmentType.map(this::mapToResponse);
    }

    private EmploymentTypeResponse mapToResponse(EmploymentType employmentType) {
        EmploymentTypeResponse response = new EmploymentTypeResponse();
        BeanUtils.copyProperties(employmentType, response);
        return response;
    }
}
