package com.droute.driverservice.dto.request;

public class UpdatePasswordRequestDTO {
    private String password;

    // Constructors
    public UpdatePasswordRequestDTO() {}

    public UpdatePasswordRequestDTO(String password) {
        this.password = password;
    }

    // Getters and Setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
