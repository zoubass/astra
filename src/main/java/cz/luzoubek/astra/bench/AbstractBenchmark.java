package cz.luzoubek.astra.bench;

import cz.luzoubek.astra.model.Message;
import java.util.Arrays;

public abstract class AbstractBenchmark implements Benchmark {

    private static final int[] volumeSizes = {256, 5000, 1000000};

    @Override
    public void runTest() {
        System.err.println("Duration; Count; Volume");

        for (int i = 0; i < 10; i++) {
            for (int volumeIndex = 0; volumeIndex < 3; volumeIndex++) {
                for (int duration = 5; duration < 11; duration += 5) {
                    final int durationInS = duration * 1000;
                    int count = runTestInternally(generateMessage(volumeIndex), durationInS);
                    System.err.println(duration + "; " + count + "; " + volumeIndex);
                    synchronized (this) {
                        try {
                            wait(3000);
                        } catch (InterruptedException e) {
                            System.out.println("Cannot wait");
                        }
                    }
                }
            }
            synchronized (this) {
                try {
                    wait(30000);
                } catch (InterruptedException e) {
                    System.out.println("Cannot wait");
                }
            }
            System.out.println("--------------------------------------------");
        }
    }

    @Override
    public void runCircleTest() {
        for (int volumeIndex = 0; volumeIndex < 3; volumeIndex++) {
            Message message = generateMessage(volumeIndex);

            for (int i = 0; i < 100; i++) {
                runCircleTestInternally(message);
                synchronized (this) {
                    try {
                        wait(500);
                    } catch (InterruptedException e) {

                    }
                }
            }
            System.err.println("Volume: " + volumeIndex);
            printStatsAndClear();
        }
    }

    private Message generateMessage(int volumeIndex) {
        int volumeSize = volumeSizes[volumeIndex];
        char[] data = new char[volumeSize];
        Arrays.fill(data, 'r');
        return new Message(new String(data));
    }

    protected abstract int runTestInternally(Message message, int duration);

    protected abstract void runCircleTestInternally(Message message);

    protected abstract void printStatsAndClear();
}
