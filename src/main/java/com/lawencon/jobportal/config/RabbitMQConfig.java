package com.lawencon.jobportal.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_TOPIC = "topic_exchange";
    public static final String EXCHANGE_QUEUE = "notif_queue";

    @Bean
    public Queue notifQueue() {
        return new Queue(EXCHANGE_QUEUE, true, false, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_TOPIC);
    }

    @Bean
    public Binding bindingNotif(Queue notifQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(notifQueue).to(topicExchange).with("*.notif");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
