package cz.luzoubek.astra.service;

import cz.luzoubek.astra.model.Message;
import java.util.ArrayList;
import java.util.List;
import javax.jms.Queue;
import javax.jms.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("active")
public class ActiveMqSender {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMqSender.class);

    private final JmsTemplate jmsTemplate;
    private final Topic topic;
    private final Queue queue;
    private static final String DEFAULT_QUEUE_NAME = "test.queue";

    private List<Long> metrics = new ArrayList<>();
    private long start;
    private long end;

    @Autowired
    public ActiveMqSender(JmsTemplate jmsTemplate, Topic topic, Queue queue) {
        this.jmsTemplate = jmsTemplate;
        this.topic = topic;
        this.queue = queue;
    }

    public void sendQueuedMessage(String message) {
        logger.info(String.format("Sending message '%s' into activemq queue.", message));
        jmsTemplate.convertAndSend(queue, message);
    }

    public void sendTopicMessage(Message message) {
        start = System.currentTimeMillis();
        jmsTemplate.convertAndSend(topic, message);
    }
    
    @JmsListener(destination = "consumer.topic")
    public void receiveFromConsumer(Message message){
//        logger.info("Receiver message from the whole circle: " + message.getText());
        end = System.currentTimeMillis();
        metrics.add(end-start);
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
