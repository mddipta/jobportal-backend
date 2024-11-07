package com.lawencon.jobportal.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplyCandidateResponse {
    private String id;
    private String vacancyId;
    private String vacancyName;
    private String dateApply;
    private List<String> attachments;
}
