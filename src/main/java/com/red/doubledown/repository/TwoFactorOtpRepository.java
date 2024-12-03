package com.red.doubledown.repository;

import com.red.doubledown.modal.TwoFactorAuth;
import com.red.doubledown.modal.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP,String> {
    TwoFactorOTP findByUserId(Long userId);
}
