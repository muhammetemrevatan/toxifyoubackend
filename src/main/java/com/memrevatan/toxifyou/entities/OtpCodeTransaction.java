package com.memrevatan.toxifyou.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "otp_code_transaction")
@NoArgsConstructor
@AllArgsConstructor
public class OtpCodeTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String otpCode;
    private String email;
    private String status;
    private long userId;
}
