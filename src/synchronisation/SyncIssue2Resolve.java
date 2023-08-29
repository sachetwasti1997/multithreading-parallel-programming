package synchronisation;

public class SyncIssue2Resolve {

    public static int counter1 = 0;
    public static int counter2 = 0;

    private static final Object object1 = new Object();
    private static final Object object2 = new Object();

    public static void increment1() {
//        System.out.println("increment1 called by thread "+Thread.currentThread().getName()
//        +" "+System.currentTimeMillis());
        synchronized (object1) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            counter1++;
        }
    }

    public static void process() {
        var t1 = new Thread(() -> {
            for (int i=0; i<100; i++) {
                increment1();
            }
        });

        var t2 = new Thread(() -> {
            for (int i=0; i<100; i++) increment2();
        });

        long startTime = System.currentTimeMillis();

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
            System.out.println("Total Time taken: "+(System.currentTimeMillis() - startTime));
            System.out.println(counter1);
            System.out.println(counter2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static synchronized void increment2() {
//        System.out.println("increment 2 called by thread "+Thread.currentThread().getName()
//                +" "+System.currentTimeMillis());
        synchronized (object2) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            counter2++;
        }
    }

    public static void main(String[] args) {
        process();
    }

}
