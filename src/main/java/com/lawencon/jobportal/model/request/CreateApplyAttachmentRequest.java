package com.lawencon.jobportal.model.request;

import com.lawencon.jobportal.persistence.entity.ApplyCandidate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateApplyAttachmentRequest {
    @NotNull
    @NotBlank
    private List<MultipartFile> attachment;

    @NotNull
    @NotBlank
    private ApplyCandidate applyCandidates;

    @NotNull
    @NotBlank
    private List<String> documentType;
}
