package com.company.restsecurity.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class UserCredentialDto {

    @NotNull(message = "field is required")
    private String username;

    @NotNull(message = "field is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String token;

    public UserCredentialDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
