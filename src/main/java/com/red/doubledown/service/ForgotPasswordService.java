package com.red.doubledown.service;

import com.red.doubledown.domain.VerificationType;
import com.red.doubledown.modal.ForgotPasswordToken;
import com.red.doubledown.modal.User;

public interface ForgotPasswordService {
    ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);

}

