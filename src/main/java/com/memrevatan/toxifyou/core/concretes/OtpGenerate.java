package com.memrevatan.toxifyou.core.concretes;

import lombok.NoArgsConstructor;

import java.util.Random;

public class OtpGenerate {

    public static String generateOtpCode() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10)); // Sadece sayılar kullanarak rastgele bir hane oluşturulur
        }
        return otp.toString();
    }
}
