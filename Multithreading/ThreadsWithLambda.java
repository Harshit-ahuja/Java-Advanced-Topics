/*
Since the Runnable interface has only one abstract method, a lambda expression provides 
a concise way to implement it.
Therefore, threads created using lambdas are not a different threading mechanism; 
they are simply a simplified form of creating threads by implementing the Runnable interface.
*/
public class ThreadsWithLambda {
    public static void main(String[] args) {

        /*
        There is a constructor in java which requires a Runnable object to create threads:
            Constructor - Thread(Runnable target)
        Hence, Thread needs a Runnable object in which the run() method is defined.
        |------------------------------|
        | () -> {                      |
        |    // Thread logic code here |
        | }                            | 
        |______________________________|  
        This part of lambda actually acts as a direct implementation of Runnable.
        And the thread logic code inside the curly braces is directly put inside the run() method
        of Runnable object by the compiler.
        */
        Thread t1 = new Thread(() -> {
            for(int i = 1; i <= 3; i++) {
                System.out.println("Thread 1 : " + i);
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i = 1; i <= 3; i++) {
                System.out.println("Thread 2 : " + i);
            }
        });

        Thread t3 = new Thread(() -> {
            for(int i = 1; i <= 3; i++) {
                System.out.println("Thread 3 : " + i);
            }
        });

        // Custom thread name can be provided like this when creating threads with lambdas
        Thread t4 = new Thread(() -> {
            for(int i = 1; i <= 3; i++) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
            }
        }, "Harshit's-thread");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}