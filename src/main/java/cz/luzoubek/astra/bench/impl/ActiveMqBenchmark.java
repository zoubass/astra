package cz.luzoubek.astra.bench.impl;

import cz.luzoubek.astra.bench.AbstractBenchmark;
import cz.luzoubek.astra.model.Message;
import cz.luzoubek.astra.service.ActiveMqSender;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("active")
public class ActiveMqBenchmark extends AbstractBenchmark {

    private ActiveMqSender sender;

    @Autowired
    public ActiveMqBenchmark(ActiveMqSender sender) {
        this.sender = sender;
    }

    @Override
    protected int runTestInternally(Message message, int durationInMs) {

        long before = System.currentTimeMillis();
        long actual = 0;
        int count = 0;

        while (actual - before < durationInMs) {
            actual = System.currentTimeMillis();
            sender.sendTopicMessage(message);
            count++;
        }

        return count;
    }

    @Override
    protected void runCircleTestInternally(Message message) {
        sender.sendTopicMessage(message);
    }

    @Override
    protected void printStatsAndClear() {
        List<Long> metrics = sender.getMetrics();

        long sum = 0;
        for (Long duration : metrics) {
            sum += duration;
        }
        double average = sum / metrics.size();
        System.err.println(average);
        sender.clearMetrics();
    }
}
