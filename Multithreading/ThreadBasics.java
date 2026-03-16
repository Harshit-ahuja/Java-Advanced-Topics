class MyThread extends Thread {

    // Constructor provided to set custom thread name
    public MyThread(String name) {
        super(name); // passes custom name to Thread class
    }

    // Default Constructor
    public MyThread() {
        super();
    }

    public void myMethod() {
        System.out.println("myMethod won't execute automatically when the thread starts");
    }

    public void run() {
        System.out.println("run method executed automatically when new thread - " + Thread.currentThread().getName() + " was started");
        for(int i = 1; i<=5; i++) {
            System.out.println(Thread.currentThread().getName() + " - " + i);
        }
    }
}

public class ThreadBasics {
    public static void main(String[] args) throws InterruptedException{

        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        MyThread t3 = new MyThread("Harshit's-Thread-3"); // set custom name of a thread through its constructor

        t1.start(); // starts new thread, will call run() method internally when the thread is started
        t2.start();
        t3.start();

        // main thread waits for t1, t2 and t3 to finish as we use join method
        t1.join();
        t2.join();
        t3.join();

        System.out.println("All 3 thread finished execution. Main thread continues...");

    }
}