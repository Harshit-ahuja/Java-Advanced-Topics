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