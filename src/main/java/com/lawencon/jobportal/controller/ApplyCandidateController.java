package com.lawencon.jobportal.controller;

import java.util.List;

import com.lawencon.jobportal.model.response.ApplyCandidateResponseDetail;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.CreateApplyCandidateRequest;
import com.lawencon.jobportal.model.response.ApplyCandidateResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.ApplyCandidateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;

@Tag(name = "Role", description = "Role API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class ApplyCandidateController {
    private final ApplyCandidateService service;

    @RolesAllowed({"KD"})
    @GetMapping(value = "/apply-vacancies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<ApplyCandidateResponse>>> get() {
        return ResponseEntity.ok(ResponseHelper.ok(service.getByUserIdLogin()));
    }

    @RolesAllowed({"HR", "SA"})
    @GetMapping(value = "/apply-vacancies/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<ApplyCandidateResponse>>> getByUserId(
            @PathVariable String userId) {
        return ResponseEntity.ok(ResponseHelper.ok(service.getByUserId(userId)));
    }

    @RolesAllowed({"KD"})
    @PostMapping(value = "/apply-vacancies", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebResponse<String>> create(
            @ModelAttribute CreateApplyCandidateRequest request) {
        service.create(request);
        return ResponseEntity.ok(ResponseHelper.ok("Apply candidate created successfully"));
    }

    @RolesAllowed({"KD"})
    @GetMapping(value = "/apply-vacancies/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<ApplyCandidateResponseDetail>> detail(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(service.detail(id)));
    }
}
