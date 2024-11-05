package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.lawencon.jobportal.authentication.model.UserPrinciple;
import com.lawencon.jobportal.model.request.CreateUserRequest;
import com.lawencon.jobportal.model.request.LoginRequest;
import com.lawencon.jobportal.model.request.RegisterUserRequest;
import com.lawencon.jobportal.model.request.UpdateUserRequest;
import com.lawencon.jobportal.model.request.VerificationOtpRequest;
import com.lawencon.jobportal.model.response.UserResponse;
import com.lawencon.jobportal.persistence.entity.Role;
import com.lawencon.jobportal.persistence.entity.User;
import com.lawencon.jobportal.persistence.repository.UserRepository;
import com.lawencon.jobportal.service.EmailService;
import com.lawencon.jobportal.service.OtpService;
import com.lawencon.jobportal.service.RoleService;
import com.lawencon.jobportal.service.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final EmailService emailService;

    @Override
    public Optional<User> login(LoginRequest request) {
        Optional<User> user = repository.findByUsername(request.getUsername());
        if (user.isPresent() && user.get().getIsActive()) {
            if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Wrong username or password");
            }
            return user;
        } else if (user.isPresent() && !user.get().getIsActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not active");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong username or password");
        }
    }

    @Override
    public void createUser(CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setIsActive(true);

        Optional<Role> role = roleService.getEntityById(request.getRole());
        if (role.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found");
        }
        user.setRole(role.get());

        repository.save(user);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username)
                    throws UsernameNotFoundException {
                User user = repository.findByUsername(username)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                "User not found"));
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.getRole().getCode()));
                return UserPrinciple.builder().user(user).authorities(authorities)
                        .role(user.getRole()).build();
            }
        };
    }

    @Override
    public void registerUser(RegisterUserRequest request) {
        // Register User for candidate
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setIsActive(false);

        Optional<Role> role = roleService.getByCode("KD");
        if (role.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found");
        }
        user.setRole(role.get());
        user = repository.saveAndFlush(user);
        String code = otpService.create(user.getId());

        repository.saveAndFlush(user);

        emailService.sendOtpEmail(code, request.getEmail());
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<User> getEntityById(String id) {
        return repository.findById(id);
    }

    @Override
    public void update(UpdateUserRequest request) {
        Optional<User> user = repository.findById(request.getId());
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setUsername(request.getUsername());
            updatedUser.setEmail(request.getEmail());
            updatedUser.setVersion(updatedUser.getVersion() + 1);

            if (request.getIsActive() != null) {
                updatedUser.setIsActive(request.getIsActive());
            }

            if (request.getPassword() != null) {
                updatedUser.setPassword(passwordEncoder.encode(request.getPassword()));
            }

            Optional<Role> role = roleService.getEntityById(request.getRole());
            if (role.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found");
            }
            updatedUser.setRole(role.get());

            repository.saveAndFlush(updatedUser);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
    }

    @Override
    public List<UserResponse> getAll() {
        List<UserResponse> responses = new ArrayList<>();
        List<User> users = repository.findAll();
        users.forEach(user -> {
            responses.add(mapToResponse(user));
        });
        return responses;
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public void validateOtp(VerificationOtpRequest request) {
        Boolean isValid = otpService.validate(request.getCode(), request.getUserId());
        if (!isValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP");
        }
        Optional<User> user = repository.findById(request.getUserId());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

        User updatedUser = user.get();
        updatedUser.setIsActive(true);
        repository.saveAndFlush(updatedUser);
    }

    @Override
    public UserResponse getById(String id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            return mapToResponse(user.get());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setRole(user.getRole().getName());
        response.setRoleId(user.getRole().getId());
        BeanUtils.copyProperties(user, response);
        return response;
    }



}
