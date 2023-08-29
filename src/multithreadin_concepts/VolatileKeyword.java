package multithreadin_concepts;

class Worker implements Runnable {
    public volatile boolean terminate; // volatile is used so that this variable is
    // in main memory and visible to every thread

    @Override
    public void run() {
        while (!terminate) {
            System.out.println("Worker class is running ...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void terminate() {
        this.terminate = true;
    }
}

public class VolatileKeyword {
    public static void main(String[] args) {
        var worker = new Worker();
        Thread t1 = new Thread(worker);
        t1.start();
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        worker.terminate();
        System.out.println("Terminating thread");
    }
}
