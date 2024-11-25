package com.lawencon.jobportal.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateStageProcessRequest {
    @NotBlank
    @NotNull
    private String userId;

    @NotBlank
    @NotNull
    private String vacancyId;

    @NotBlank
    @NotNull
    private String stage;

    @NotBlank
    @NotNull
    private MultipartFile questionFile;

    @NotBlank
    @NotNull
    private ZonedDateTime date;
}
