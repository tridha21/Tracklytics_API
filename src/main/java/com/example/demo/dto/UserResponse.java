package com.example.demo.dto;


public class UserResponse {

    private Long userId;
    private String userEmail;
    private String userRole;

    public UserResponse(Long userId, String userEmail, String userRole) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userRole = userRole;
    }

    public Long getId() { return userId; }
    public String getUserEmail() { return userEmail; }
    public String getRole() { return userRole; }
}
