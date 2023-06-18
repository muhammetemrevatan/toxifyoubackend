package com.memrevatan.toxifyou.core.configuration;

import com.memrevatan.toxifyou.core.httpResponse.success.ApiSuccess;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage() {
        var a = new ApiSuccess(200, "User created!", "/api/1.0/users");
        rabbitTemplate.convertAndSend("default", "queueName", a);
    }
}
