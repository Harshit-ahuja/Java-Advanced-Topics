class SharedResource {
    volatile boolean flag = false;
}

public class BusyWaiting {
    public static void main(String[] args) {
        SharedResource sr = new SharedResource();

        // Consumer Thread
        Thread consumer = new Thread(() -> {
            System.out.println("Consumer waiting for data...");

            while (!sr.flag) {
                // Busy waiting (spinning)
            }

            System.out.println("Consumer got the signal!");
        });

        // Producer Thread
        Thread producer = new Thread(() -> {
            try {
                Thread.sleep(6000); // simulate work
            } catch (InterruptedException e) {}

            sr.flag = true;
            System.out.println("Producer set the flag!");
        });

        consumer.start();
        producer.start();
    }
}

/*
Busy Waiting (Spinning) occurs in the consumer thread here.

-> The consumer thread continuously checks the condition !sr.flag
-> While flag is updated, it keeps looping without sleeping or releasing CPU
-> This keeps the CPU busy even though the thread is doing no useful work

This is exactly the definition of 'Busy Waiting' :
A thread repeatedly checks a condition in a loop instead of waiting efficiently, wasting CPU resources.
*/