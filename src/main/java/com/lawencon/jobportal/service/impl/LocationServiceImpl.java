package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.lawencon.jobportal.model.request.CreateLocationRequest;
import com.lawencon.jobportal.model.request.UpdateLocationRequest;
import com.lawencon.jobportal.model.response.LocationResponse;
import com.lawencon.jobportal.persistence.entity.Location;
import com.lawencon.jobportal.persistence.repository.LocationRepository;
import com.lawencon.jobportal.service.LocationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository repository;

    @Override
    public List<LocationResponse> getAll() {
        List<LocationResponse> responses = new ArrayList<>();
        List<Location> locations = repository.findAll();
        locations.forEach(location -> {
            responses.add(mapToResponse(location));
        });
        return responses;
    }

    @Override
    public Optional<LocationResponse> getByCode(String code) {
        Optional<Location> location = repository.findByCode(code);
        if (location.isPresent()) {
            return Optional.of(mapToResponse(location.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Location> getEntityById(String id) {
        Optional<Location> location = repository.findById(id);
        return location;
    }

    @Override
    public void create(CreateLocationRequest request) {
        Location location = new Location();
        location.setCode(request.getCode());
        location.setName(request.getName());
        location.setIsActive(true);
        repository.save(location);
    }

    @Override
    public void update(UpdateLocationRequest request) {
        Optional<Location> location = repository.findById(request.getId());
        if (location.isPresent()) {
            Location updatedLocation = location.get();
            updatedLocation.setName(request.getName());
            updatedLocation.setIsActive(request.getIsActive());
            updatedLocation.setVersion(updatedLocation.getVersion() + 1);
            repository.saveAndFlush(updatedLocation);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "location is not exist");
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    private LocationResponse mapToResponse(Location location) {
        LocationResponse response = new LocationResponse();
        BeanUtils.copyProperties(location, response);
        return response;
    }

    @Override
    public LocationResponse getById(String id) {
        Optional<Location> location = repository.findById(id);
        if (location.isPresent()) {
            return mapToResponse(location.get());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "location is not exist");
    }
}
