package com.lawencon.jobportal.model.request;

import org.springframework.web.multipart.MultipartFile;
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
public class UpdateUserCertifiationRequest {
    @NotBlank
    @NotNull
    private String id;

    private MultipartFile file;

    @NotNull
    @NotBlank
    private String name;
}
