package com.lawencon.jobportal.authentication.helper;

import com.lawencon.jobportal.authentication.model.UserPrinciple;
import com.lawencon.jobportal.persistence.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionHelper {
    public static User getLoginUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserPrinciple) {
            return ((UserPrinciple) authentication.getPrincipal()).getUser();
        } else {
            return null;
        }
    }

    public static UserPrinciple getUserPrinciple() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
