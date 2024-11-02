package com.lawencon.jobportal.model.request.user;

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
public class UpdateUserRequest {
    @NotBlank
    @NotNull
    private String id;

    @NotNull
    @NotBlank
    private String username;

    @Null
    private String password;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String role;

    @Null
    private Boolean isActive;
}
