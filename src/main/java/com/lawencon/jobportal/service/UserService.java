package com.lawencon.jobportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.lawencon.jobportal.model.request.CreateUserRequest;
import com.lawencon.jobportal.model.request.LoginRequest;
import com.lawencon.jobportal.model.request.RegisterUserRequest;
import com.lawencon.jobportal.model.request.UpdateUserRequest;
import com.lawencon.jobportal.model.request.VerificationOtpRequest;
import com.lawencon.jobportal.model.response.UserResponse;
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

    UserResponse getById(String id);
}
