package cz.luzoubek.astra.controller;

import cz.luzoubek.astra.bench.Benchmark;
import cz.luzoubek.astra.model.Message;
import cz.luzoubek.astra.service.ApacheKafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Profile("kafka")
@RestController
public class KafkaController {

    @Autowired
    private ApacheKafkaSender kafkaSender;

    @Autowired
    private Benchmark benchmark;
    
    @GetMapping("/kafka/run_circle_test")
    public String runCircleTest(){
        benchmark.runCircleTest();
        return "TEST COMPLETED";
    }

    @PostMapping("/kafka/send")
    public String sendKafkaMessage(@RequestBody Message message) {
        kafkaSender.sendMessage(message);
        return "OK";
    }

    @GetMapping("/kafka/run_test")
    public String runRabbitTestAuto() {
        benchmark.runTest();
        return "TEST COMPLETED";
    }
}
