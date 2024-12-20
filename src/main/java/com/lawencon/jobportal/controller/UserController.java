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
import com.lawencon.jobportal.model.request.CreateUserRequest;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.ResendOtpVerificationRequest;
import com.lawencon.jobportal.model.request.UpdateUserRequest;
import com.lawencon.jobportal.model.request.VerificationOtpRequest;
import com.lawencon.jobportal.model.response.UserResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "User", description = "User API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<UserResponse>>> getAll(PagingRequest pagingRequest,
            @RequestParam(required = false) String inquiry) {
        return ResponseEntity
                .ok(ResponseHelper.ok(pagingRequest, service.getAll(pagingRequest, inquiry)));
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<UserResponse>> getById(@Valid @PathVariable String id) {
        return ResponseEntity.ok(ResponseHelper.ok(service.getById(id)));
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> create(@RequestBody CreateUserRequest request) {
        service.createUser(request);
        return ResponseEntity.ok(ResponseHelper.ok("User has been created successfully"));
    }

    @PutMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> update(@RequestBody UpdateUserRequest request) {
        service.update(request);
        return ResponseEntity.ok(ResponseHelper.ok("User has been updated successfully"));
    }

    @DeleteMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(ResponseHelper.ok("User has been deleted successfully"));
    }

    @PutMapping(value = "/users/verification", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> verification(
            @Valid @RequestBody VerificationOtpRequest request) {
        service.validateOtp(request);
        return ResponseEntity.ok(ResponseHelper.ok("User has been verified successfully"));
    }

    @PostMapping(value = "/users/verification/resend", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> resend(
            @RequestBody ResendOtpVerificationRequest request) {
        service.resendOtp(request);
        return ResponseEntity.ok(ResponseHelper.ok("Otp has been resent successfully"));
    }
}
