package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class TaskF implements Runnable {
    private int id;

    public TaskF(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Task with id "+id+" is in work thread - id: "
                +Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}

public class FixedThreadPoolsExecutors {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        long start = System.currentTimeMillis();
        for (int i=0; i<12; i++) {
            service.execute(new TaskF(i));
        }
        var m = service.awaitTermination(3000, TimeUnit.MILLISECONDS);
        System.out.println("Time taken "+(System.currentTimeMillis() - start));
    }
}
