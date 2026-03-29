class OddEvenPrinter {
    private int number = 1;
    private final int limit = 10;

    public synchronized void print(boolean isOddThread) {
        while(number <= limit) {
            System.out.println(Thread.currentThread().getName() + " validated loop condition");
            if(isOddThread) {
                // Wait for Even Numbers
                while(number % 2 == 0) {
                    try{
                        System.out.println(Thread.currentThread().getName() + " waits at line 12");
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }              
            } else {
                // Wait for Odd Numbers
                while(number % 2 != 0) {
                    try{
                        System.out.println(Thread.currentThread().getName() + " waits at line 22");
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }   
            }

            // Printing number
            if(number <= limit) {
                System.out.println(Thread.currentThread().getName() + " : " + number);
            }

            number++;
            notifyAll();
        }
    }
}

public class InterThreadCommunication {
    public static void main(String[] args) {
        OddEvenPrinter printer = new OddEvenPrinter();

        // Creating odd thread
        Thread t1 = new Thread(() -> {
            printer.print(true);
        }, "Odd-Thread");

        // Creating even thread
        Thread t2 = new Thread(() -> {
            printer.print(false);
        }, "Even-Thread");

        t1.start();
        t2.start();
    }
}

// 

