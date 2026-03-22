public class ThreadPriority {
    public static void main(String[] args) throws InterruptedException{
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " : Running");
        }, "Low-Priority-Thread");

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " : Running");
        }, "Norm-Priority-Thread");

        Thread t3 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " : Running");
        }, "High-Priority-Thread");

        // Set priorities
        t1.setPriority(Thread.MIN_PRIORITY);   // 1
        t2.setPriority(Thread.NORM_PRIORITY);  // 5
        t3.setPriority(Thread.MAX_PRIORITY);   // 10

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Main thread waiting for all 3 threads
        t1.join();
        t2.join();
        t3.join();

        System.out.println("3 Threads do not necessarily run in the order of priorities defined");
    }
}