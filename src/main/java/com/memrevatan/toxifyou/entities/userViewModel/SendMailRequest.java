package com.memrevatan.toxifyou.entities.userViewModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMailRequest {
    private String to;
    private String subject;
    private String body;
}
