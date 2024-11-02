package com.lawencon.jobportal.model.request.location;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLocationRequest {
    @NotBlank
    private String code;

    @NotBlank
    private String name;
}
