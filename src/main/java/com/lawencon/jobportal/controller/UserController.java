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
import com.lawencon.jobportal.model.request.CreateUserRequest;
import com.lawencon.jobportal.model.request.UpdateUserRequest;
import com.lawencon.jobportal.model.request.VerificationOtpRequest;
import com.lawencon.jobportal.model.response.UserResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Location", description = "Location API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<UserResponse>>> getAll() {
        return ResponseEntity.ok(ResponseHelper.ok(service.getAll()));
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
}
