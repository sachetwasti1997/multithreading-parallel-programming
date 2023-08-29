package multithreadin_concepts;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class WorkersDR {
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    public void work1() {
        lock1.lock();
        System.out.println("Worker 1 acquires lock 1");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        lock2.lock();
        System.out.println("Worker 1 acquires lock 2");

        lock1.unlock();
        lock2.unlock();
    }

    public void work2() {
        try {
            lock1.lock();
            System.out.println("Worker 2 acquires lock 1");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            lock2.lock();
            System.out.println("Worker 2 acquires lock 2");
        } finally {
            lock1.unlock();
            lock2.unlock();
        }
    }
}

public class DeadLockResolve {
    public static void main(String[] args) {
        var workers = new WorkersDR();

        new Thread(workers::work1, "Worker1").start();
        new Thread(workers::work2, "Worker2").start();
    }
}
