package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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
import com.lawencon.jobportal.helper.SpecificationHelper;
import com.lawencon.jobportal.helper.ValidateForeignKey;
import com.lawencon.jobportal.model.request.CreateUserRequest;
import com.lawencon.jobportal.model.request.LoginRequest;
import com.lawencon.jobportal.model.request.PagingRequest;
import com.lawencon.jobportal.model.request.RegisterUserRequest;
import com.lawencon.jobportal.model.request.ResendOtpVerificationRequest;
import com.lawencon.jobportal.model.request.UpdateUserRequest;
import com.lawencon.jobportal.model.request.VerificationOtpRequest;
import com.lawencon.jobportal.model.response.UserResponse;
import com.lawencon.jobportal.persistence.entity.ApplyCandidate;
import com.lawencon.jobportal.persistence.entity.JobVacancyTransaction;
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
    private final ValidateForeignKey validateForeignKey;

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
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not active");
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

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password not match");
        }

        validateUsernameExist(request.getUsername());
        validateEmailExist(request.getEmail());

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
    public Optional<User> getEntityById(String id) {
        return repository.findById(id);
    }

    @Override
    public void update(UpdateUserRequest request) {
        Optional<User> user = repository.findById(request.getId());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

        if (!user.get().getUsername().equals(request.getUsername())) {
            validateUsernameExist(request.getUsername());
        }

        User updatedUser = user.get();
        updatedUser.setUsername(request.getUsername());
        updatedUser.setEmail(request.getEmail());
        updatedUser.setVersion(updatedUser.getVersion() + 1);

        if (request.getIsActive() != null) {
            updatedUser.setIsActive(request.getIsActive());
        }

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            updatedUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Optional<Role> role = roleService.getEntityById(request.getRole());
        if (role.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found");
        }
        updatedUser.setRole(role.get());

        repository.saveAndFlush(updatedUser);

    }

    @Override
    public Page<UserResponse> getAll(PagingRequest pagingRequest, String inquiry) {
        PageRequest pageRequest =
                PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize(),
                        SpecificationHelper.createSort(pagingRequest.getSortBy()));
        Specification<User> spec = Specification.where(null);
        if (inquiry != null) {
            spec = spec.and(SpecificationHelper.inquiryFilter(Arrays.asList("username"), inquiry));
        }

        Page<User> userResponses = repository.findAll(spec, pageRequest);

        List<UserResponse> responses = userResponses.getContent().stream().map(user -> {
            UserResponse userResponse = mapToResponse(user);

            return userResponse;
        }).toList();

        return new PageImpl<>(responses, pageRequest, userResponses.getTotalElements());
    }

    @Override
    public void delete(String id) {
        Boolean isHaveFkApplyJob =
                validateForeignKey.isParentIdReferenced(id, ApplyCandidate.class, "user.id");
        Boolean isHAveAssignVacancy =
                validateForeignKey.isParentIdReferenced(id, JobVacancyTransaction.class, "user.id");

        if (isHaveFkApplyJob) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User cannot be deleted because it has a reference in another table");
        }

        if (isHAveAssignVacancy) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User cannot be deleted because it has a reference in another table");
        }


        repository.deleteById(id);
    }

    @Override
    public void resendOtp(ResendOtpVerificationRequest request) {
        Optional<User> user = repository.findByEmail(request.getEmail());



        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

        if (user.get().getIsActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already active");
        }

        String code = otpService.recreateOtp(user.get().getId());
        emailService.sendOtpEmail(code, request.getEmail());
    }

    @Override
    public void validateOtp(VerificationOtpRequest request) {
        Optional<User> user = repository.findByEmail(request.getEmail());

        Boolean isValid = otpService.validate(request.getCode(), user.get().getId());
        if (!isValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP");
        }

        User updatedUser = user.get();
        updatedUser.setIsActive(true);
        repository.saveAndFlush(updatedUser);
    }

    @Override
    public UserResponse getById(String id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

        return mapToResponse(user.get());
    }

    @Override
    public Long countUser() {
        return repository.countByIsActiveTrue();
    }

    private void validateUsernameExist(String username) {
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exist");
        }
    }

    private void validateEmailExist(String email) {
        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exist");
        }
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setRole(user.getRole().getName());
        response.setRoleId(user.getRole().getId());
        BeanUtils.copyProperties(user, response);
        return response;
    }



}
