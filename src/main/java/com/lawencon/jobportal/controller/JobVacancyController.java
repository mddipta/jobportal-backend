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
import com.lawencon.jobportal.model.request.CreateJobVacancyRequest;
import com.lawencon.jobportal.model.request.SetPicToVacancyRequest;
import com.lawencon.jobportal.model.request.UpdateJobVacancyRequest;
import com.lawencon.jobportal.model.request.UpdateStatusJobVacancyRequest;
import com.lawencon.jobportal.model.response.JobVacancyResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.JobVacancyService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Job Vacancy", description = "Job Vacancy API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class JobVacancyController {
    private final JobVacancyService service;

    @GetMapping(value = "/job-vacancies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<JobVacancyResponse>>> getAll() {
        return ResponseEntity.ok(ResponseHelper.ok(service.getAll()));
    }

    @RolesAllowed({"SA"})
    @PostMapping(value = "/job-vacancies", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> create(
            @RequestBody CreateJobVacancyRequest request) {
        service.create(request);
        return ResponseEntity.ok(ResponseHelper.ok("Success"));
    }

    @RolesAllowed({"SA"})
    @PutMapping(value = "/job-vacancies", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> update(
            @RequestBody UpdateJobVacancyRequest request) {
        service.update(request);
        return ResponseEntity.ok(ResponseHelper.ok("Success"));
    }

    @RolesAllowed({"SA"})
    @DeleteMapping(value = "/job-vacancies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("Success"));
    }

    @RolesAllowed({"SA"})
    @PostMapping(value = "/job-vacancies/set-pic", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> setPic(
            @RequestBody @Valid SetPicToVacancyRequest request) {
        service.setPicToVacancy(request);
        return ResponseEntity.ok(ResponseHelper.ok("Success add pic to vacancy"));
    }

    @RolesAllowed({"HR"})
    @PostMapping(value = "/job-vacancies/publish", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> publishVacancy(
            @RequestBody @Valid UpdateStatusJobVacancyRequest request) {
        service.publishVacancy(request);
        return ResponseEntity.ok(ResponseHelper.ok("Success change status"));
    }

    @RolesAllowed({"HR", "SA"})
    @PostMapping(value = "/job-vacancies/close", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> closeVacancy(
            @RequestBody @Valid UpdateStatusJobVacancyRequest request) {
        service.publishVacancy(request);
        return ResponseEntity.ok(ResponseHelper.ok("Success change status"));
    }
}
