package multithreadin_concepts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphores are simple variables that are used to control access to a common resource
 * Semaphores are the records of how many units of a particular resource are available
 * Have to wait until a unit of record becomes available again
 * COUNTING_SEMAPHORES : allows arbitrary resource count
 * BINARY_SEMAPHORES: semaphores that are restricted to 0 and 1
 */
enum Download {
    INSTANCE;

    private final Semaphore semaphore = new Semaphore(3, true);

    public void download() {
        try {
            semaphore.acquire();
            downloadData();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            semaphore.release();
        }
    }

    private void downloadData() {
        try {
            System.out.println("Downloading data from the web");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}

/**
 * suppose library has 10 identical rooms
 * students request room front desk
 * if no rooms free students wait for rooms to be available, util someone reliquishes room
 * when finished student notifies front door
 */
public class SemaphoresImpl {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i=0; i< 12; i++) {
            service.execute(Download.INSTANCE::download);
        }
        service.shutdown();
    }
}
