package com.memrevatan.toxifyou.entities.userViewModel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpVerificationResponse {
    private boolean success;
    private long userId;
    private String email;
    private String status;
    private String message;
}
