package com.red.doubledown.service;

import com.red.doubledown.domain.VerificationType;
import com.red.doubledown.modal.User;
import com.red.doubledown.modal.VerificationCode;
import org.springframework.stereotype.Service;


public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user,VerificationType verificationType);
    VerificationCode getVerificationCodeById(Long id) throws Exception;
    VerificationCode getVerificationCodeByUser(Long id);
    void deleteVerificationCodeById(VerificationCode verificationCode);

}
