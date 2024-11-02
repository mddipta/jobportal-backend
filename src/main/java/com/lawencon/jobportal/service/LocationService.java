package com.lawencon.jobportal.service;

import java.util.List;
import java.util.Optional;

import com.lawencon.jobportal.model.request.location.CreateLocationRequest;
import com.lawencon.jobportal.model.request.location.UpdateLocationRequest;
import com.lawencon.jobportal.model.response.location.LocationResponse;
import com.lawencon.jobportal.persistence.entity.Location;

public interface LocationService {
    List<LocationResponse> getAll();

    Optional<LocationResponse> getByCode(String code);

    Optional<Location> getEntityById(String id);

    void create(CreateLocationRequest request);

    void update(UpdateLocationRequest request);

    void delete(String id);
}
