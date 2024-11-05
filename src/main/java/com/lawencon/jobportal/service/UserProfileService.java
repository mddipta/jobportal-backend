package com.lawencon.jobportal.service;

import com.lawencon.jobportal.model.request.CreateUserProfileRequest;
import com.lawencon.jobportal.model.request.UpdateUserProfileRequest;
import com.lawencon.jobportal.model.response.UserProfileResponse;

public interface UserProfileService {

    UserProfileResponse getByUserId();

    void create(CreateUserProfileRequest request);

    void update(UpdateUserProfileRequest request);
}
