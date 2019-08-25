package cz.luzoubek.astra.config;

import cz.luzoubek.astra.model.Message;
import java.util.HashMap;
import java.util.Map;
import javax.jms.Queue;
import javax.jms.Topic;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Profile("active")
@Configuration
public class ActiveMqConfig {

    private static final String TOPIC_NAME = "producer.topic";

    @Bean
    public Topic jmsTopic() {
        return new ActiveMQTopic(TOPIC_NAME);
    }

    @Bean
    public Queue activeQueue(){
        return new ActiveMQQueue("test.queue");
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put("JMS_TYPE", Message.class);

        converter.setTypeIdMappings(typeIdMappings);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("JMS_TYPE");
        return converter;
    }
}
