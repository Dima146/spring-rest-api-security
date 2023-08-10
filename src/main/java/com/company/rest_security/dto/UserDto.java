package com.company.rest_security.dto;

import com.company.rest_security.validation.FieldMatch;
import com.company.rest_security.validation.StrongPassword;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class UserDto {

    private Long id;

    @NotNull(message = "Username is required")
    @Size(min = 4, max = 15, message = "Username should be between 4 and 15 characters")
    private String username;

    @NotNull(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password should be at least 8 characters")
    @StrongPassword
    private String password;

    private String matchingPassword;

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}


