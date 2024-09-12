package com.example.bookstore.dto;

import jakarta.validation.constraints.*;

public class RegistersUser {

    @NotBlank(message = "This is required information")
    @Size(min = 6, message = "Minimum length is 6")
    private String username;

    @NotBlank(message = "This is required information")
    @Size(min = 8, message = "Minimum length is 8")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[@%^#$&+=!])(?=\\S+$).*$",
            message = "Password must have at least 1 number and 1 special character"
    )
    private String password;

    @NotBlank(message = "This is required information")
    @Email(message = "")
    private String email;

    @NotBlank(message = "This is required information")
    private String phoneNumber;

    @NotBlank(message = "This is required information")
    private String address;

    public RegistersUser() {
    }

    public RegistersUser(String username, String password, String email, String phoneNumber, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
