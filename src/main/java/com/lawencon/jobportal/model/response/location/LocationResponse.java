package com.lawencon.jobportal.model.response.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {
    private String id;
    private String code;
    private String name;
}
