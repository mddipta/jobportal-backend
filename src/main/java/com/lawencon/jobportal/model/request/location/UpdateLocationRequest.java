package com.lawencon.jobportal.model.request.location;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLocationRequest {
    @NotNull
    @NotBlank
    private String id;

    @NotNull
    @NotBlank
    private String name;

    @Null
    private Boolean isActive;
}
