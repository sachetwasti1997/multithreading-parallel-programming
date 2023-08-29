package basics;

import java.util.concurrent.atomic.AtomicInteger;

class DaemonWorker implements Runnable {
    @Override
    public void run() {
        AtomicInteger atomicInt = new AtomicInteger(0);
        while (true) {
            try {

                Thread.sleep(10);
                System.out.println("The daemon Thread is running..."+atomicInt.incrementAndGet());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class NormalThread implements Runnable {
    @Override
    public void run() {
            try {
                Thread.sleep(3000);
                System.out.println("Okay the normal thread stopped...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
    }
}

public class DeamonTh {

    public static void main(String[] args) {
        Thread t1 = new Thread(new DaemonWorker());
        Thread t2 = new Thread(new NormalThread());
        t1.setDaemon(true);
        t1.start();
        t2.start();
    }

}
