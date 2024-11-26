package com.lawencon.jobportal.authentication.service;

import com.lawencon.jobportal.model.request.LoginRequest;
import com.lawencon.jobportal.model.response.JwtAuthenticationResponse;
import com.lawencon.jobportal.model.response.UserResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse login(LoginRequest loginRequest);

    UserResponse getDataLogin();
}
