package synchronisation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProducerConsumer {

    private static List<Integer> arrList = new ArrayList<>();
    private final static int UPPER_LIMIT = 10;
    private final static int LOWER_LIMIT = 0;
    private final static Random rand = new Random(100);
    private static final Object lock = new Object();

    public static void produce() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                Thread.sleep(10);
                if (arrList.size() == UPPER_LIMIT) {
                    System.out.println("Waiting for the consumer thread to remove items, as the upper " +
                            "limit is reached");
                    lock.wait();
                } else {
                    int itm = rand.nextInt(100);
                    System.out.println("Adding item ["+Thread.currentThread().getName()+"] "+itm);
                    arrList.add(itm);
                    //call to notify other thread notified when this thread stops and other thread waiting
                    lock.notify();
                }
            }
        }
    }

    public static void consume() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                Thread.sleep(500);
                if (arrList.isEmpty()) {
                    System.out.println("Waiting for the producer thread to add items, as the lower " +
                            "limit is reached");
                    lock.wait();
                } else {
                    System.out.println("Removing item ["+Thread.currentThread().getName()+"] "
                            +arrList.remove(arrList.size()-1));
                    lock.notify();
                }
            }
        }
    }

    public static void main(String[] args) {
        var t1 = new Thread(() -> {
            try {
                produce();
            } catch (InterruptedException e) {
                System.out.println("Caught Exception "+e.getMessage());
            }
        });
        var t2 = new Thread(() -> {
            try {
                consume();
            } catch (InterruptedException e) {
                System.out.println("Caught Exception "+e.getMessage());
            }
        });

        t1.start();
        t2.start();
    }

}
