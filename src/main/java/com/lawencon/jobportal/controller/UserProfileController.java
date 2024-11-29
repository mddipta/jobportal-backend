package com.lawencon.jobportal.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.CreateUserProfileRequest;
import com.lawencon.jobportal.model.request.UpdateUserProfileRequest;
import com.lawencon.jobportal.model.response.UserDetailResponse;
import com.lawencon.jobportal.model.response.UserProfileResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.UserProfileService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Role", description = "Role API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class UserProfileController {
    private final UserProfileService service;

    @GetMapping(value = "/user-profiles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<UserProfileResponse>> get() {
        return ResponseEntity.ok(ResponseHelper.ok(service.getByUserLogin()));
    }

    @GetMapping(value = "/user-profiles/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<UserDetailResponse>> getById(
            @Valid @PathVariable String userId) {
        return ResponseEntity.ok(ResponseHelper.ok(service.getDetailUser(userId)));
    }

    @PostMapping(value = "/user-profiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebResponse<String>> createUserProfile(
            @ModelAttribute CreateUserProfileRequest request) {
        service.create(request);
        return ResponseEntity.ok(ResponseHelper.ok("User profile created successfully"));
    }

    @PutMapping(value = "/user-profiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebResponse<String>> updateUserProfile(
            @ModelAttribute UpdateUserProfileRequest request) {
        service.update(request);
        return ResponseEntity.ok(ResponseHelper.ok("User profile updated successfully"));
    }
}
