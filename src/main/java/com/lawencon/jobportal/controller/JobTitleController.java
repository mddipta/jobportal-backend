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
import com.lawencon.jobportal.model.request.jobtitle.CreateJobTitle;
import com.lawencon.jobportal.model.request.jobtitle.UpdateJobTitle;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.model.response.jobtitle.JobTitleDetailResponse;
import com.lawencon.jobportal.model.response.jobtitle.JobTitleResponse;
import com.lawencon.jobportal.service.JobTitleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;

@Tag(name = "Job Title", description = "Job Title API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class JobTitleController {
    final private JobTitleService service;

    @GetMapping(value = "/job-titles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<JobTitleResponse>>> getAll() {
        return ResponseEntity.ok(ResponseHelper.ok(service.getAll()));
    }

    @GetMapping(value = "/job-title/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<JobTitleDetailResponse>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(service.getById(id)));
    }

    @RolesAllowed({"SA"})
    @PostMapping(value = "/job-title", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> create(@RequestBody CreateJobTitle request) {
        service.create(request);
        return ResponseEntity.ok(ResponseHelper.ok("Success"));
    }

    @RolesAllowed({"SA"})
    @PutMapping(value = "/job-title", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> update(@RequestBody UpdateJobTitle request) {
        service.update(request);
        return ResponseEntity.ok(ResponseHelper.ok("Success"));
    }

    @RolesAllowed({"SA"})
    @DeleteMapping(value = "/job-title/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("Success"));
    }
}
