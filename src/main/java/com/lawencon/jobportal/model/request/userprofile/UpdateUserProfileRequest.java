package com.lawencon.jobportal.model.request.userprofile;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.lawencon.jobportal.constant.GenderType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProfileRequest {
    @Null
    private MultipartFile profilePicture;

    @Null
    private MultipartFile fileCv;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String phone;

    @NotNull
    @NotBlank
    private String address;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    private GenderType gender;

    @NotNull
    private LocalDate born;
}
