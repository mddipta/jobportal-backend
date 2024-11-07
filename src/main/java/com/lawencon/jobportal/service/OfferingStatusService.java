package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.response.OfferingStatusResponse;
import com.lawencon.jobportal.persistence.entity.OfferingStatus;

import java.util.List;

public interface OfferingStatusService {
    List<OfferingStatusResponse> getAll();

    OfferingStatusResponse getById(String id);

    OfferingStatusResponse getByCode(String code);

    OfferingStatus getEntityById(String id);
}
