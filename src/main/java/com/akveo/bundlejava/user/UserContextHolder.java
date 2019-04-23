package com.akveo.bundlejava.user;

import com.akveo.bundlejava.authentication.BundleUserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContextHolder {
    public static User getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BundleUserDetailsService.BundleUserDetails userDetails = (BundleUserDetailsService.BundleUserDetails) principal;
        return userDetails.getUser();
    }
}
