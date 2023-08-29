package basics;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class Runner1S extends Thread {
    @Override
    public void run() {
        var rand = new Random();
        AtomicInteger integer = new AtomicInteger(0);
        IntStream.generate(() -> rand.nextInt(100))
                .limit(50)
                .forEach((s) -> System.out.println("Runner 1: "+s
                        +" count:"+integer.incrementAndGet()+" thread:"+
                        Thread.currentThread().getName()));
    }
}

class Runner2S extends Thread {
    @Override
    public void run() {
        var rand = new Random();
        AtomicInteger integer = new AtomicInteger(0);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        IntStream.generate(() -> rand.nextInt(100))
                .limit(50)
                .forEach((s) -> System.out.println("Runner 1: "+s
                        +" count:"+integer.incrementAndGet()+" thread:"+
                        Thread.currentThread().getName()));
    }
}

public class StartThread {
    public static void main(String[] args) {
        var thread = new Runner1S();
        var thread2 = new Runner2S();
        thread2.start();
        thread.start();
    }
}
