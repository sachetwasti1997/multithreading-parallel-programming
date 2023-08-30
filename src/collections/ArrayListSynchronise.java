package collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ArrayListSynchronise {

    public static void main(String[] args) {
        var list = Collections.synchronizedList(new ArrayList<>());

        var t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                var rand = new Random();
                for (int i=0; i<1000; i++) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    list.add(rand.nextInt(100));
                }
            }
        });

        var t2 = new Thread(() -> {
            var rand = new Random();
            for (int i=0; i<1000; i++) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                list.add(rand.nextInt(100));
            }
        });

        var start = System.currentTimeMillis();

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
            System.out.println("Size of the lists "+list.size());
            System.out.println("Time Taken "+(System.currentTimeMillis() - start));
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

}
