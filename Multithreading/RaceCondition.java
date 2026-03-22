class Counter {
    int count = 0;

    void increment() {
        count++; // not thread-safe
    }
}

public class RaceCondition {
    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                c.increment();
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Expected Count : 2000 ");
        System.out.println("Actual Final Count : " + c.count + " due to race condition");
    }
}

/*
Cause of Race Condition :

1. Multiple threads are modifying a shared variable (count) simultaneously.
   count++ is not atomic; it has 3 steps:
    -> Read current value
    -> Increment
    -> Write back
   Thread can interleave in the middle of any of these steps, causing unpredictable 
   final counts.

2. Role of local CPU cache.
    -> Threads may read count from their local CPU cache instead of main memory.
    -> Cache can exacerbate the problem, because a thread may see a stale value.
   Important: Local CPU cache of threads is not the root cause of race condition. The root
   cause is still the non-atomic nature of count++ operation.
   Cache just makes it more likely that threads see stale values temporarily, which can 
   trigger updates lost faster.

Example of operations that leads to Race Condition under the hood :

Thread A reads count = 0 (A Interleaves)
Thread B reads count = 0 (B Interleaves)
Thread A writes count = 1 (A Interleaves)
Thread B writes count = 1 
(B doesn’t re-read after A writes, B already read count as 0, so it incremented the count to 1 
and overwrites the value written by A, which was also 1)

👉 Expected result: 2
👉 Actual result: 1 (lost update)

This is race condition.
*/