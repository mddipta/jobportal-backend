package com.lawencon.jobportal.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationOtpRequest {
    @NotNull
    @NotBlank
    private String code;

    @NotNull
    @NotBlank
    private String userId;
}
