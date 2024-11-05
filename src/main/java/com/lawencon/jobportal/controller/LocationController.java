package com.lawencon.jobportal.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.CreateLocationRequest;
import com.lawencon.jobportal.model.request.UpdateLocationRequest;
import com.lawencon.jobportal.model.response.LocationResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.LocationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;

@Tag(name = "Location", description = "Location API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class LocationController {
    final private LocationService service;


    @GetMapping(value = "/locations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<LocationResponse>>> getAll() {
        return ResponseEntity.ok(ResponseHelper.ok(service.getAll()));
    }

    @RolesAllowed({"SA"})
    @PostMapping(value = "/locations", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> create(@RequestBody CreateLocationRequest request) {
        service.create(request);
        return ResponseEntity.ok(ResponseHelper.ok("User has been created successfully"));
    }

    @RolesAllowed({"SA"})
    @PutMapping(value = "/locations", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody UpdateLocationRequest request) {
        service.update(request);
        return ResponseEntity.ok("Success");
    }

    @RolesAllowed({"SA"})
    @DeleteMapping(value = "/locations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok("Success");
    }
}
