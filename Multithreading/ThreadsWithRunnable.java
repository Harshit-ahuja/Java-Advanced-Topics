/*
'Runnable' is a functional interface in java used for multithreading.
Functional Interface - Interface that contains just one abstract method but can contain multiple defaults or static methods.
Also called 'SAM' Interface - Single Abstract Method Interface
Runnable interface has one abstract method - 'run()' which needs to be overrided while implementing Runnable.
'run()' method should define the actual logic that the thread needs to perform.
*/

class MyRunnableTask implements Runnable {

    @Override
    public void run() {
        System.out.println("Task is running in thread: "+ Thread.currentThread().getName());
    }

}

public class ThreadsWithRunnable {
    public static void main(String[] args) {
        MyRunnableTask runnableTask = new MyRunnableTask();

        Thread t1 = new Thread(runnableTask);
        Thread t2 = new Thread(runnableTask);

        t1.start();
        t2.start();
    }
}