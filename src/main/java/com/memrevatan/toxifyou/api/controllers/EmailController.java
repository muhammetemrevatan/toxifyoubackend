package com.memrevatan.toxifyou.api.controllers;

import com.memrevatan.toxifyou.business.abstracts.EmailService;
import com.memrevatan.toxifyou.core.concretes.AmqpProducer;
import com.memrevatan.toxifyou.core.configuration.MessageSender;
import com.memrevatan.toxifyou.core.httpResponse.success.ApiSuccess;
import com.memrevatan.toxifyou.entities.userViewModel.OtpVerification;
import com.memrevatan.toxifyou.entities.userViewModel.OtpVerificationResponse;
import com.memrevatan.toxifyou.entities.userViewModel.SendMailRequest;
import com.rabbitmq.client.AMQP;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    private final AmqpTemplate rabbitTemplate;
    private final DirectExchange exchange;

    @Value("${sample.rabbitmq.queue}")
    String queueName;
    @Value("${sample.rabbitmq.routingKey}")
    String routingKey;


    @PostMapping("/send-mail")
    public void sendMail(@RequestBody SendMailRequest request) throws MessagingException {
        this.emailService.sendEmail(request.getTo(),request.getSubject(), request.getBody());
    }

    @PostMapping("/send-otp-mail")
    public void otpMail(@RequestBody SendMailRequest request) {
        this.emailService.sendOtpCodeEmail(request.getTo(), 999999L);
    }

    @PostMapping("/otp-code-verification")
    public OtpVerificationResponse otpVerification(@RequestBody OtpVerification otpVerification) {
        return emailService.otpVerification(otpVerification);
    }

    @PostMapping("/rabbit")
    public void rabbit() {
        rabbitTemplate.convertAndSend(exchange.getName(),routingKey,"Emre");
    }
}
