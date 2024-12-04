package com.red.doubledown.request;

import com.red.doubledown.domain.VerificationType;
import lombok.Data;

@Data
public class ForgotPasswordTokenReq {
    private String sendTO;
    private VerificationType verificationType;


}
