package com.footballiq.demo.dto;

public class RegisterRequest {

    private String email;
    private String password;
    private String fullName;
    private String preferredTeam;

    public RegisterRequest() {
    }

    public RegisterRequest(String email, String password, String fullName, String preferredTeam) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.preferredTeam = preferredTeam;
    }

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPreferredTeam() {
        return preferredTeam;
    }

    public void setPreferredTeam(String preferredTeam) {
        this.preferredTeam = preferredTeam;
    }
}