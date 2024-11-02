package com.lawencon.jobportal.model.request.jobtitle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateJobTitle {
    private String id;
    private String title;
    private Boolean isActive;
}
