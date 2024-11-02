package com.lawencon.jobportal.service;

public interface EmailService {
    void sendOtpEmail(String code, String target);
}
