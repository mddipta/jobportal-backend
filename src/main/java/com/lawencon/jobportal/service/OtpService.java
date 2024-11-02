package com.lawencon.jobportal.service;

public interface OtpService {
    String create(String userId);

    Boolean validate(String code, String userId);
}
