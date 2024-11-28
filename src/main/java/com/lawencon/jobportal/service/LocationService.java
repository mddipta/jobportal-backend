package com.lawencon.jobportal.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import com.lawencon.jobportal.model.request.CreateLocationRequest;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.UpdateLocationRequest;
import com.lawencon.jobportal.model.response.LocationResponse;
import com.lawencon.jobportal.persistence.entity.Location;

public interface LocationService {
    Page<LocationResponse> getAll(PagingRequest pagingRequest, String inquiry);

    Optional<LocationResponse> getByCode(String code);

    Optional<Location> getEntityById(String id);

    void create(CreateLocationRequest request);

    void update(UpdateLocationRequest request);

    void delete(String id);

    LocationResponse getById(String id);
}
