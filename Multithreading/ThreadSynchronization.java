class SynchronizedCounter {
    int count = 0;

    // Synchronized method
    synchronized void increment() {
        System.out.println(Thread.currentThread().getName() + " increased counter");
        count++;
    }
}

public class ThreadSynchronization {
    public static void main(String[] args) throws InterruptedException {
        SynchronizedCounter sc = new SynchronizedCounter();

        Runnable task = () -> {
            for(int i = 0; i < 500; i++) {
                sc.increment();
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Expected counter : 1000");
        System.out.println("Actual Synchronized Counter : " + sc.count);
    }
}

/*
 1. We use a synchronized method in this code.

 2. The keyword synchronized ensures mutual exclusion :
        -> Only one thread at a time can execute the increment() method on the same object (sc).
        -> Other threads trying to call increment() on the same object will wait until the first 
           thread exits the method.

 3. Think of it like a lock on the object : 
        -> When a thread enters increment(), it acquires the monitor lock of sc.
        -> While the lock is held, no other thread can enter any synchronized method of that same object.
        -> When the thread exits increment(), it releases the lock, allowing another thread to enter.

        Note - While one thread holds the monitor lock of sc, no other thread can enter any 
        synchronized method of sc object, but other threads can still execute non-synchronized 
        method of sc.

 4. The keyword synchronized also provides memory visibility :
        -> Entering a synchronized block: the thread invalidates its working memory / cache and 
           reads the latest values from main memory.
        -> Exiting a synchronized block: the thread flushes all its updates to main memory, 
           making them visible to other threads.
    
        This ensures that updates to thread by one thread are visible to other threads.

 5. Thread switching between Loop Iterations  :
        -> A thread does not hold the lock for the entire loop; it acquires the lock only when 
           entering a synchronized method.
        -> After executing one call of the synchronized method (e.g., increment()), the lock is 
           released.
        -> At this point, the CPU can switch to another thread, which may call the same 
           synchronized method.
        -> Threads can interleave between iterations of a loop; the order of execution is 
           non-deterministic.
*/