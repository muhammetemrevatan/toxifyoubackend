package com.memrevatan.toxifyou.business.abstracts;

import com.memrevatan.toxifyou.entities.userViewModel.OtpVerification;
import com.memrevatan.toxifyou.entities.userViewModel.OtpVerificationResponse;

import javax.mail.MessagingException;

public interface EmailService {
    void sendEmail(String to, String subject, String body) throws MessagingException;

    void sendOtpCodeEmail(String to, long userId);

    OtpVerificationResponse otpVerification(OtpVerification otpVerification);
}
