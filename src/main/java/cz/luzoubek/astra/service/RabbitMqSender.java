package cz.luzoubek.astra.service;

import static cz.luzoubek.astra.config.RabbitMqConfig.PRODUCER_QUEUE;

import cz.luzoubek.astra.model.Message;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("rabbit")
public class RabbitMqSender {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqSender.class);

    private static final String ROUTING_KEY = "consumer.queue";
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange topicExchange;

    private List<Long> metrics = new ArrayList<>();
    private long start;
    private long end;

    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate, TopicExchange outgoingExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.topicExchange = outgoingExchange;
    }

    public void sendMessage(Message message) {
        start = System.currentTimeMillis();
        rabbitTemplate.convertAndSend(topicExchange.getName(), ROUTING_KEY, message);
    }

    @RabbitListener(queues = PRODUCER_QUEUE)
    public void receive(Message message) {
        end = System.currentTimeMillis();
        metrics.add(end - start);
    }

    public List<Long> getMetrics() {
        return metrics;
    }

    public void clearMetrics() {
        start = 0;
        end = 0;
        metrics = new ArrayList<>();
    }
}
