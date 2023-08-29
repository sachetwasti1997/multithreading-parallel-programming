package synchronisation;

public class SyncIssue {

    public static int counter = 0;

    /**
     * Each thread that works here will try to acquire the intrinsic lock of the
     * `SyncIssue` object;
     * As long as a thread owns an intrinsic lock no other thread can acquire the
     * same lock.
     * The other thread will block when it tries to acquire the lock.
     */
    public static synchronized void increment() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        counter ++;
    }

    public static void process() {
        var t1 = new Thread(() -> {
            for (int i=0; i<100; i++) {
                increment();
            }
        });

        var t2 = new Thread(() -> {
            for (int i=0; i<100; i++) increment();
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
            System.out.println(counter);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        process();
    }

}
