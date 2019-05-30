package javapractice_server;

public class MyRunnable implements Runnable {
    public void run() {
        // System.out.println("top o' the stack: 1"); //lecture exercise
        for(int i=0; i<10000 ; i++) {//this code test's which thread print to system.out and how much line is executed
            System.out.println("in run: "+i);
        }
    }

    public static void main(String[] args) {
        Runnable threadJob = new MyRunnable();
        Thread myThread = new Thread(threadJob);
        myThread.start();
        // System.out.println("back in main"); //lecture exercise
        for(int i=0; i<10000 ; i++) {//this code test's which thread print to system.out and how much line is executed
            System.out.println("in main: "+i);
        }
    }
}