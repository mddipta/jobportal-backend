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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.CreateJobSpecification;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.UpdateJobSpecification;
import com.lawencon.jobportal.model.response.JobSpecificationResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.JobSpecificationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;

@Tag(name = "Job Specification Title", description = "Job Specification Title API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class JobSpecificationController {
    private final JobSpecificationService service;

    @RolesAllowed({"SA"})
    @PostMapping(value = "/job-specifications", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> create(@RequestBody CreateJobSpecification request) {
        service.createSingle(request);
        return ResponseEntity.ok(ResponseHelper.ok("Success"));
    }

    @RolesAllowed({"SA"})
    @GetMapping(value = "/job-specifications/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<JobSpecificationResponse>>> getByJobTitleId(
            PagingRequest pagingRequest, @RequestParam(required = false) String inquiry,
            @PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(pagingRequest,
                service.jobSpecListPage(pagingRequest, inquiry, id)));
    }

    @RolesAllowed({"SA"})
    @GetMapping(value = "/job-specifications/edit/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<JobSpecificationResponse>> edit(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(service.edit(id)));
    }

    @RolesAllowed({"SA"})
    @PutMapping(value = "/job-specifications", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> update(@RequestBody UpdateJobSpecification request) {
        service.update(request);
        return ResponseEntity.ok(ResponseHelper.ok("Success"));
    }

    @RolesAllowed({"SA"})
    @DeleteMapping(value = "/job-specifications/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("Success"));
    }
}
