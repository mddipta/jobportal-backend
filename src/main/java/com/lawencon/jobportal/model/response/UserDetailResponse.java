package com.lawencon.jobportal.model.response;

import com.lawencon.jobportal.constant.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {
    private String id;
    private String username;
    private String email;
    private String roleCode;
    private String role;
    private String profilePic;
    private String name;
    private String phone;
    private String address;
    private String city;
    private GenderType gender;
    private String born;
    private Boolean isActive;
}
