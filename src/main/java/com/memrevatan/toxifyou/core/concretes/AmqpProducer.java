package com.memrevatan.toxifyou.core.concretes;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class AmqpProducer {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    @Autowired
    public AmqpProducer(RabbitTemplate rabbitTemplate, @Lazy Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(queue.getName(), message);
    }

    @Bean
    public Queue queue() {
        return new Queue("mail-queue");
    }
}
