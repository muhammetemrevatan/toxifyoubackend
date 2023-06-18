package com.memrevatan.toxifyou.core.configuration;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {
    @RabbitListener(queues = "${sample.rabbitmq.queue}")
    public void receiveMessage(Object message) {
        System.out.println("RABBIT MQ -> Received message: " + message);
    }
}
