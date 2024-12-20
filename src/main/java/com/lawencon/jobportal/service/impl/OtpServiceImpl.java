package com.lawencon.jobportal.service.impl;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Random;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.lawencon.jobportal.persistence.entity.Otp;
import com.lawencon.jobportal.persistence.repository.OtpRepository;
import com.lawencon.jobportal.service.OtpService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final OtpRepository otpRepository;

    private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int LENGTH = 6;

    private String generateOtp() {
        StringBuilder otp = new StringBuilder(LENGTH);
        Random random = new Random();
        for (int i = 0; i < LENGTH; i++) {
            otp.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return otp.toString();
    }


    @Override
    public String create(String userId) {
        Otp otp = new Otp();
        otp.setCode(generateOtp());
        otp.setUserId(userId);
        otpRepository.save(otp);

        return otp.getCode();
    }

    @Override
    public Boolean validate(String code, String userId) {
        Optional<Otp> otp = otpRepository.findByCodeAndUserId(code, userId);
        ZonedDateTime now = ZonedDateTime.now();

        if (otp.isEmpty()) {
            return false;
        }

        Duration duration = Duration.between(otp.get().getCreatedAt(), now);
        if (duration.toMinutes() > 30) {
            otpRepository.delete(otp.get());
            return false;
        }

        otpRepository.delete(otp.get());
        return true;
    }


    @Override
    public String recreateOtp(String userId) {
        Optional<Otp> otp = otpRepository.findByUserId(userId);

        if (otp.isPresent()) {
            Duration duration = Duration.between(otp.get().getCreatedAt(), ZonedDateTime.now());
            if (duration.toMinutes() < 3) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Cant resend otp, please wait 3 minutes");
            }
        }

        if (otp.isPresent()) {
            otpRepository.delete(otp.get());
        }

        return create(userId);
    }

}
