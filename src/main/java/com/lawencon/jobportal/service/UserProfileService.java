package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.userprofile.CreateUserProfileRequest;
import com.lawencon.jobportal.model.response.userprofile.UserProfileResponse;
import com.lawencon.jobportal.persistence.entity.UserProfile;

import java.util.Optional;

public interface UserProfileService {
    Optional<UserProfile> getEntityById(String id);

    UserProfileResponse getByUserId(String id);

    void create(CreateUserProfileRequest request);

    void update(CreateUserProfileRequest request);
}
