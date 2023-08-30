package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Task implements Runnable {
    private int id;

    public Task(int id) {
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

public class SingleThreadedExecutor {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        long start = System.currentTimeMillis();
        for (int i=0; i<5; i++) {
            service.execute(new Task(i));
        }
        System.out.println("Time taken "+(System.currentTimeMillis() - start));
    }
}
