package com.lawencon.jobportal.model.response;


import com.lawencon.jobportal.constant.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private String profilePic;
    private String cvFile;
    private String name;
    private String phone;
    private String address;
    private String city;
    private GenderType gender;
    private LocalDate born;
    private Long version;
}
