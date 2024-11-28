package com.lawencon.jobportal.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessSelectionStageResponse {
    private String name;
    private String selectionStage;
    private String selectionDate;
    private String vacancy;
}
