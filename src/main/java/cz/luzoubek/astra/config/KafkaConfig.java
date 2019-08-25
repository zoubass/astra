package cz.luzoubek.astra.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Profile("kafka")
@Configuration
public class KafkaConfig {


    @Bean
    public NewTopic kafkaTopic() {
        return new NewTopic("producer.topic", 100, new Short("1"));
    }

    @Bean
    public StringJsonMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }
}
