package com.lawencon.jobportal.controller;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.CreateUserCertificationRequest;
import com.lawencon.jobportal.model.request.UpdateUserCertifiationRequest;
import com.lawencon.jobportal.model.response.UserCertificationResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.UserCertificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;



@Tag(name = "User Certification", description = "User Certification API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class UserCertificationController {
    private final UserCertificationService service;

    @GetMapping(value = "/certifications", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<UserCertificationResponse>>> get() {
        return ResponseEntity.ok(ResponseHelper.ok(service.getByUserId()));
    }

    @GetMapping(value = "/certifications/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<UserCertificationResponse>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(service.getById(id)));
    }

    @PostMapping(value = "/certifications", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebResponse<String>> create(
            @ModelAttribute CreateUserCertificationRequest request) {
        service.create(request);
        return ResponseEntity.ok(ResponseHelper.ok("User certification created successfully"));
    }

    @PutMapping(value = "/certifications", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebResponse<String>> update(
            @ModelAttribute UpdateUserCertifiationRequest request) {
        service.update(request);
        return ResponseEntity.ok(ResponseHelper.ok("User certification updated successfully"));
    }

    @DeleteMapping(value = "/certifications/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("User certification deleted successfully"));
    }
}
