package com.lawencon.jobportal.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StageProcessResponse {
    private String id;
    private String userId;
    private String nameCandidate;
    private String vacancyId;
    private String vacancyName;
    private String status;
    private String score;
    private String date;
    private String version;
    private String questionFile;
    private String answerFile;
    private String stage;
}
