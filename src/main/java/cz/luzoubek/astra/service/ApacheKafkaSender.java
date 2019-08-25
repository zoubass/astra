package cz.luzoubek.astra.service;

import cz.luzoubek.astra.model.Message;
import java.util.ArrayList;
import java.util.List;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Profile("kafka")
@Service
public class ApacheKafkaSender {

    private static final Logger logger = LoggerFactory.getLogger(ApacheKafkaSender.class);

    private KafkaTemplate kafkaTemplate;
    private NewTopic topic;

    private List<Long> metrics = new ArrayList<>();
    private long start;
    private long end;

    @Autowired
    public ApacheKafkaSender(KafkaTemplate kafkaTemplate, NewTopic topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendMessage(Message message) {
        start = System.currentTimeMillis();
        kafkaTemplate.send(topic.name(), message);
    }

    @KafkaListener(topics = "consumer.topic", groupId = "producerGroup")
    public void consume(Message message) {
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
