package com.red.doubledown.request;


import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String otp;
    private String password;
}