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

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Expected Count : 2000 ");
        System.out.println("Actual Final Count : " + c.count + " due to race condition");
    }
}

/*
Understanding of what may be happening under the hood :

Thread A reads count = 0
Thread B reads count = 0
Thread A writes count = 1
Thread B writes count = 1

👉 Expected result: 2
👉 Actual result: 1 (lost update)

This is a race condition.
*/