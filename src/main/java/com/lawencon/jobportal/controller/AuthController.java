package com.lawencon.jobportal.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.jobportal.authentication.service.AuthenticationService;
import com.lawencon.jobportal.helper.ResponseHelper;
import com.lawencon.jobportal.model.request.LoginRequest;
import com.lawencon.jobportal.model.request.RegisterUserRequest;
import com.lawencon.jobportal.model.response.JwtAuthenticationResponse;
import com.lawencon.jobportal.model.response.UserResponse;
import com.lawencon.jobportal.model.response.WebResponse;
import com.lawencon.jobportal.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;


@Tag(name = "Auth", description = "Auth API endpoint")
@RestController
@RequestMapping({"/api/v1"})
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authService;
    private final UserService userService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<JwtAuthenticationResponse>> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ResponseHelper.ok(authService.login(request)));
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> createUser(
            @RequestBody RegisterUserRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok(ResponseHelper.ok("User has been created successfully"));
    }

    @GetMapping(value = "/user-login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<UserResponse>> getUserLogin() {
        return ResponseEntity.ok(ResponseHelper.ok(authService.getDataLogin()));
    }

}
