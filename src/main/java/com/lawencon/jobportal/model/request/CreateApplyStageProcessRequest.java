package com.lawencon.jobportal.model.request;

import com.lawencon.jobportal.persistence.entity.JobVacancy;
import com.lawencon.jobportal.persistence.entity.SelectionStage;
import com.lawencon.jobportal.persistence.entity.SelectionStageStatus;
import com.lawencon.jobportal.persistence.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateApplyStageProcessRequest {
    @NotNull
    @NotBlank
    private User user;

    @NotNull
    @NotBlank
    private JobVacancy vacancy;

    @NotNull
    @NotBlank
    private SelectionStage stage;

    @NotBlank
    @NotNull
    private SelectionStageStatus status;

    @NotBlank
    @NotNull
    private ZonedDateTime date;
}
