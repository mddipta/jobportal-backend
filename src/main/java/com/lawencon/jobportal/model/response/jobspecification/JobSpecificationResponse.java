package com.lawencon.jobportal.model.response.jobspecification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobSpecificationResponse {
    private String id;
    private String specification;
    private String titleId;
}
