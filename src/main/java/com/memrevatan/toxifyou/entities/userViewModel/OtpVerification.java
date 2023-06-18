package com.memrevatan.toxifyou.entities.userViewModel;

import com.memrevatan.toxifyou.core.EmailStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerification {
    private long userId;
    private String email;
    private String otpCode;
}
