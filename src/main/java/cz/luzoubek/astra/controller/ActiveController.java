package cz.luzoubek.astra.controller;

import cz.luzoubek.astra.bench.Benchmark;
import cz.luzoubek.astra.model.Message;
import cz.luzoubek.astra.service.ActiveMqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Profile("active")
@RestController
public class ActiveController {

    @Autowired
    private ActiveMqSender activeMqSender;

    @Autowired
    private Benchmark benchmark;

    @PostMapping("/activemq/queue/send")
    public String sendActiveMqQueueMessage(@RequestBody String message) {
        activeMqSender.sendQueuedMessage(message);
        return "OK";
    }

    @PostMapping("/activemq/topic/send")
    public String sendActiveMqTopicMessage(@RequestBody Message message) {
        activeMqSender.sendTopicMessage(message);
        return "OK";
    }

    @GetMapping("/activemq/run_test")
    public String runRabbitTestAuto() {
        System.err.println("Duration; Count; Volume");
        benchmark.runTest();
        return "TEST COMPLETED";
    }

    @GetMapping("/activemq/run_circle_test")
    public String runCircleTest(){
        benchmark.runCircleTest();
        return "TEST COMPLETED";
    }
}
