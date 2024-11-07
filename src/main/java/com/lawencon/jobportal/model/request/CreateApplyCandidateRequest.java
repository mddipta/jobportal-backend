package com.lawencon.jobportal.model.request;

import java.util.List;
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
public class CreateApplyCandidateRequest {
    @NotNull
    @NotBlank
    private String vacancyId;

    @NotNull
    @NotBlank
    private List<MultipartFile> attachments;

    @NotNull
    @NotBlank
    private List<String> documentTypes;
}
