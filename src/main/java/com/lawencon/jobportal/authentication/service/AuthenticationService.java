package com.lawencon.jobportal.authentication.service;

import com.lawencon.jobportal.model.request.user.LoginRequest;
import com.lawencon.jobportal.model.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse login(LoginRequest loginRequest);
}