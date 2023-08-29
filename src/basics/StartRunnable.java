package basics;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class Runner1 implements Runnable {
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

class Runner2 implements Runnable {
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

public class StartRunnable {
    public static void main(String[] args) {
        var runner1 = new Runner1();
        Thread t1 = new Thread(runner1);
        Thread t2 = new Thread(runner1);
        t1.start();
        t2.start();
    }
}
