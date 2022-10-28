package com.solinftec.email.domain;

public class EmailStructure {
    private String username;

    private String email;

    public EmailStructure(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public EmailStructure() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EmailStructure{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
