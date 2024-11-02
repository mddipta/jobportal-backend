package com.lawencon.jobportal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.jobportal.service.JobStatusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.model.response.jobstatus.JobStatusResponse;

@Tag(name = "Job Status", description = "Job Status API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class JobStatusController {
    private final JobStatusService service;

    @RolesAllowed({"SA"})
    @GetMapping(value = "/job-statuses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<JobStatusResponse>>> findAll() {
        return ResponseEntity.ok(ResponseHelper.ok(service.getAll()));
    }

    @RolesAllowed({"SA"})
    @GetMapping(value = "/job-status/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<JobStatusResponse>> findById(@PathVariable String code) {
        return ResponseEntity.ok(ResponseHelper.ok(service.getByCode(code)));
    }
}
