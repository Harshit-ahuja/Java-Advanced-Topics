public class YieldExample {
    public static void main(String[] args) throws InterruptedException{

        Runnable task = () -> {
            for(int i = 1; i <=5; i++) {
                System.out.println(Thread.currentThread().getName() + " running");
                Thread.yield();
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        System.out.println(
            "Execution order is non-deterministic;\n" +
            "yield() hints the scheduler to give other runnable threads of same priority a chance to execute,\n" +
            "so alternate interleaving may be observed but alternation is not guaranteed.\n"
        );
    
        t1.start();
        t2.start();

        // Wait till execution of both threads is completed
        t1.join();
        t2.join();

        System.out.println("\n--------------------------------------------------------------------------------------------------");
        System.out.println(
            "Execution order is non-deterministic;\n" +
            "yield() may allow higher priority threads to get more CPU time,\n" +
            "so they may run more frequently, but this behavior is platform-dependent and not guaranteed.\n"
        );

        // t3 -> Higher Priority Thread
        Thread t3 = new Thread(task, "Thread-3");
        t3.setPriority(10);

        // t4 -> Lower Priority Thread
        Thread t4 = new Thread(task, "Thread-4");
        t4.setPriority(5);

        t3.start();
        t4.start();
    }
}