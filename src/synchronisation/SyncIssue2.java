package synchronisation;

public class SyncIssue2 {

    public static int counter1 = 0;
    public static int counter2 = 0;

    /**
     * Each thread that works here will try to acquire the intrinsic lock of the
     * `SyncIssue` object;
     * As long as a thread owns an intrinsic lock no other thread can acquire the
     * same lock.
     * The other thread will block when it tries to acquire the lock.
     * This is a problem when intrinsic lock of one object is used given
     * we have independent tasks.
     */
    public static synchronized void increment1() {
//        System.out.println("increment1 called by thread "+Thread.currentThread().getName()
//        +" "+System.currentTimeMillis());
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        counter1 ++;
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
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        counter2 ++;
    }

    public static void main(String[] args) {
        process();
    }

}
