package com.lawencon.jobportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lawencon.jobportal.authentication.helper.SessionHelper;
import com.lawencon.jobportal.model.request.CreateUserCertificationRequest;
import com.lawencon.jobportal.model.request.UpdateUserCertifiationRequest;
import com.lawencon.jobportal.model.response.UserCertificationResponse;
import com.lawencon.jobportal.persistence.entity.File;
import com.lawencon.jobportal.persistence.entity.User;
import com.lawencon.jobportal.persistence.entity.UserCertification;
import com.lawencon.jobportal.persistence.repository.UserCertificationRepository;
import com.lawencon.jobportal.service.FileService;
import com.lawencon.jobportal.service.UserCertificationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserCertificationServiceImpl implements UserCertificationService {
    private final UserCertificationRepository repository;

    private final FileService fileService;

    @Override
    public List<UserCertificationResponse> getByUserId() {
        User currentUser = SessionHelper.getLoginUser();
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        List<UserCertificationResponse> responses = new ArrayList<>();
        List<UserCertification> userCertifications = repository.findByUserId(currentUser.getId());
        userCertifications.forEach(userCertification -> {
            String fileUrl = null;
            if (userCertification.getCertifFile() != null) {
                fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploads/")
                        .path(userCertification.getCertifFile().getFile()).toUriString();
                userCertification.getCertifFile().setFile(fileUrl);
            }
            UserCertificationResponse response = mapToResponse(userCertification);
            response.setFile(fileUrl);
            responses.add(response);
        });

        return responses;
    }

    @Override
    public void create(CreateUserCertificationRequest request) {
        User currentUser = SessionHelper.getLoginUser();
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        List<MultipartFile> files = new ArrayList<>();
        List<String> types = new ArrayList<>();

        // Saving files
        files.add(request.getFile());
        types.add("document");
        List<File> fileEntities = fileService.save(files, types);

        UserCertification userCertification = new UserCertification();
        userCertification.setUser(currentUser);
        userCertification.setName(request.getName());
        userCertification.setCertifFile(fileEntities.get(0));

        repository.saveAndFlush(userCertification);
    }

    @Override
    public void update(UpdateUserCertifiationRequest request) {
        User currentUser = SessionHelper.getLoginUser();

        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        Optional<UserCertification> userCertification = repository.findById(request.getId());
        if (userCertification.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User certification not found");
        }

        if (request.getFile() != null) {
            fileService.update(request.getFile(), "document",
                    userCertification.get().getCertifFile().getId());
        }

        UserCertification userCertificationEntity = userCertification.get();
        userCertificationEntity.setUser(currentUser);
        userCertificationEntity.setName(request.getName());

        repository.saveAndFlush(userCertificationEntity);
    }

    @Override
    public UserCertificationResponse getById(String id) {
        Optional<UserCertification> userCertification = repository.findById(id);

        if (userCertification.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User certification not found");
        }

        if (!userCertification.get().getUser().getId()
                .equals(SessionHelper.getLoginUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authorized");
        }

        String fileUrl = null;
        if (userCertification.get().getCertifFile() != null) {
            fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploads/")
                    .path(userCertification.get().getCertifFile().getFile()).toUriString();
        }


        UserCertificationResponse response = mapToResponse(userCertification.get());
        response.setFile(fileUrl);
        return response;
    }

    @Override
    public void delete(String id) {
        Optional<UserCertification> userCertification = repository.findById(id);
        if (userCertification.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User certification not found");
        }

        fileService.delete(userCertification.get().getCertifFile().getId());
        repository.delete(userCertification.get());
    }

    private UserCertificationResponse mapToResponse(UserCertification userCertification) {
        UserCertificationResponse response = new UserCertificationResponse();
        BeanUtils.copyProperties(userCertification, response);
        return response;
    }

}
