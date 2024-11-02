package com.lawencon.jobportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.lawencon.jobportal.model.request.user.CreateUserRequest;
import com.lawencon.jobportal.model.request.user.LoginRequest;
import com.lawencon.jobportal.model.request.user.RegisterUserRequest;
import com.lawencon.jobportal.model.request.user.UpdateUserRequest;
import com.lawencon.jobportal.model.request.user.VerificationOtpRequest;
import com.lawencon.jobportal.model.response.user.UserResponse;
import com.lawencon.jobportal.persistence.entity.User;

public interface UserService {
    List<UserResponse> getAll();

    Optional<User> login(LoginRequest request);

    void createUser(CreateUserRequest request);

    void update(UpdateUserRequest request);

    UserDetailsService userDetailsService();

    void registerUser(RegisterUserRequest request);

    Optional<User> getByUsername(String username);

    Optional<User> getEntityById(String id);

    void delete(String id);

    void validateOtp(VerificationOtpRequest request);
}
