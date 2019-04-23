package com.akveo.bundlejava.authentication;

import com.akveo.bundlejava.email.ValidEmail;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RegisterRequest {
    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @AssertTrue
    private Boolean terms;

    private Boolean announcements = false;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }

    public Boolean getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Boolean announcements) {
        this.announcements = announcements;
    }
}
