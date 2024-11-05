package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.response.EmploymentTypeResponse;
import com.lawencon.jobportal.persistence.entity.EmploymentType;

import java.util.List;
import java.util.Optional;

public interface EmploymentTypeService {
    List<EmploymentTypeResponse> getAll();

    Optional<EmploymentTypeResponse> getByCode(String code);

    Optional<EmploymentType> getEntityById(String id);
}
