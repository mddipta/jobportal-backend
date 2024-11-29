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
import com.lawencon.jobportal.model.request.CreateUserProfileRequest;
import com.lawencon.jobportal.model.request.UpdateUserProfileRequest;
import com.lawencon.jobportal.model.response.UserDetailResponse;
import com.lawencon.jobportal.model.response.UserProfileResponse;
import com.lawencon.jobportal.persistence.entity.File;
import com.lawencon.jobportal.persistence.entity.User;
import com.lawencon.jobportal.persistence.entity.UserProfile;
import com.lawencon.jobportal.persistence.repository.UserProfileRepository;
import com.lawencon.jobportal.service.FileService;
import com.lawencon.jobportal.service.UserProfileService;
import com.lawencon.jobportal.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository repository;
    private final UserService userService;
    private final FileService fileService;

    @Override
    public UserProfileResponse getByUserLogin() {
        User currentUser = SessionHelper.getLoginUser();

        Optional<UserProfile> userProfile = repository.findByUserId(currentUser.getId());
        if (userProfile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User profile not found");
        }

        UserProfileResponse response = mapToResponse(userProfile.get());

        if (userProfile.get().getProfilePicture() != null) {
            String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploads/")
                    .path(userProfile.get().getProfilePicture().getFile()).toUriString();
            response.setProfilePic(fileUrl);
        }
        if (userProfile.get().getFileCv() != null) {
            String cvFileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/").path(userProfile.get().getFileCv().getFile()).toUriString();
            response.setCvFile(cvFileUrl);
        }

        return response;
    }

    @Override
    public UserProfileResponse getByUserId(String userId) {
        Optional<UserProfile> userProfile = repository.findByUserId(userId);
        if (userProfile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User profile not found");
        }

        UserProfileResponse response = mapToResponse(userProfile.get());

        if (userProfile.get().getProfilePicture() != null) {
            String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploads/")
                    .path(userProfile.get().getProfilePicture().getFile()).toUriString();
            response.setProfilePic(fileUrl);
        }
        if (userProfile.get().getFileCv() != null) {
            String cvFileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/").path(userProfile.get().getFileCv().getFile()).toUriString();
            response.setCvFile(cvFileUrl);
        }

        return response;
    }

    @Override
    public void create(CreateUserProfileRequest request) {
        UserProfile profileUser = new UserProfile();

        User currentUser = SessionHelper.getLoginUser();
        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        Optional<User> user = userService.getEntityById(currentUser.getId());
        if (user.isPresent()) {
            profileUser.setUser(user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

        List<MultipartFile> files = new ArrayList<>();
        List<String> types = new ArrayList<>();

        // Saving files
        files.add(request.getProfilePicture());
        files.add(request.getFileCv());
        types.add("image");
        types.add("document");
        List<File> fileEntities = fileService.save(files, types);

        profileUser.setName(request.getName());
        profileUser.setPhone(request.getPhone());
        profileUser.setAddress(request.getAddress());
        profileUser.setCity(request.getCity());
        profileUser.setBorn(request.getBorn());
        profileUser.setGender(request.getGender());

        profileUser.setProfilePicture(fileEntities.get(0));
        profileUser.setFileCv(fileEntities.get(1));

        repository.save(profileUser);
    }

    @Override
    public void update(UpdateUserProfileRequest request) {
        User currentUser = SessionHelper.getLoginUser();

        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        Optional<UserProfile> profileUser = repository.findByUserId(currentUser.getId());
        UserProfile userProfileEntity = profileUser.get();

        if (profileUser.isPresent()) {
            userProfileEntity = profileUser.get();
            if (request.getProfilePicture() != null) {
                fileService.update(request.getProfilePicture(), "image",
                        userProfileEntity.getProfilePicture().getId());
            }

            if (request.getFileCv() != null) {
                fileService.update(request.getFileCv(), "document",
                        userProfileEntity.getFileCv().getId());
            }

            userProfileEntity.setName(request.getName());
            userProfileEntity.setPhone(request.getPhone());
            userProfileEntity.setAddress(request.getAddress());
            userProfileEntity.setCity(request.getCity());
            userProfileEntity.setBorn(request.getBorn());
            userProfileEntity.setGender(request.getGender());
            userProfileEntity.setVersion(userProfileEntity.getVersion() + 1);

            if (userProfileEntity.getProfilePicture() == null) {
                userProfileEntity.setProfilePicture(profileUser.get().getProfilePicture());
            }
            if (userProfileEntity.getFileCv() == null) {
                userProfileEntity.setFileCv(profileUser.get().getFileCv());
            }

            repository.saveAndFlush(userProfileEntity);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User profile not found");

    }


    private UserProfileResponse mapToResponse(UserProfile userProfile) {
        UserProfileResponse response = new UserProfileResponse();
        BeanUtils.copyProperties(userProfile, response);
        return response;
    }

    @Override
    public UserDetailResponse getDetailUser(String userId) {
        Optional<User> user = userService.getEntityById(userId);
        UserProfileResponse userProfile = getByUserId(userId);

        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

        return mapToDetailResponse(user.get(), userProfile);
    }

    private UserDetailResponse mapToDetailResponse(User user, UserProfileResponse userProfile) {
        UserDetailResponse response = new UserDetailResponse();
        response.setRoleCode(user.getRole().getCode());
        response.setRole(user.getRole().getName());
        response.setProfilePic(userProfile.getProfilePic());
        response.setName(userProfile.getName());
        response.setPhone(userProfile.getPhone());
        response.setAddress(userProfile.getAddress());
        response.setCity(userProfile.getCity());
        response.setGender(userProfile.getGender());
        response.setBorn(userProfile.getBorn().toString());
        response.setIsActive(user.getIsActive());

        BeanUtils.copyProperties(user, response);
        return response;
    }


}
