package com.red.doubledown.response;


import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private boolean status;
    private String message;
    private boolean twofactorAuth;
    private String session;
}
