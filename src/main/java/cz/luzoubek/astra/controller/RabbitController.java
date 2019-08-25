package cz.luzoubek.astra.controller;

import cz.luzoubek.astra.bench.Benchmark;
import cz.luzoubek.astra.model.Message;
import cz.luzoubek.astra.service.RabbitMqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("rabbit")
public class RabbitController {


    @Autowired
    private RabbitMqSender rabbitMqSender;

    @Autowired
    private Benchmark benchmark;

    @GetMapping("/rabbitmq/run_circle_test")
    public String runCircleTest(){
        benchmark.runCircleTest();
        return "TEST COMPLETED";
    }

    @GetMapping("/rabbitmq/run_test")
    public String runRabbitTestAuto() {
        benchmark.runTest();
        return "TEST COMPLETED";
    }

    @PostMapping("/rabbitmq/send")
    public String sendRabbitMqMessage(@RequestBody Message message) {
        rabbitMqSender.sendMessage(message);
        return "OK";
    }
    
    
}
