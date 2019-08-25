package cz.luzoubek.astra.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("rabbit")
@Configuration
public class RabbitMqConfig {

    private static final String FORWARD_EXCHANGE = "rabbit.forward";
    private static final String BACKWARD_EXCHANGE = "rabbit.backward";
    public static final String PRODUCER_QUEUE = "producer.queue";

    @Bean
    public TopicExchange outgoingExchange() {
        return new TopicExchange(FORWARD_EXCHANGE, false, true);
    }

    @Bean
    public Queue rabbitProducerQueue() {
        return new Queue(PRODUCER_QUEUE);
    }

    @Bean
    public TopicExchange incomingExchange() {
        return new TopicExchange(BACKWARD_EXCHANGE, false, true);
    }

    @Bean
    public Binding binding(Queue rabbitProducerQueue, TopicExchange incomingExchange) {
        return BindingBuilder.bind(rabbitProducerQueue).to(incomingExchange).with(PRODUCER_QUEUE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2MessageConverter());
        return rabbitTemplate;
    }
    @Bean
    public Jackson2JsonMessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
