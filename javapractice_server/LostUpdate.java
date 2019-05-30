package javapractice_server;

public class LostUpdate implements Runnable {
    private int balance;
    public synchronized void run() {//add "synchronized" to method for making the method atomic
        for(int i = 0 ; i < 50 ; i++) {
            balance++;
            // increment();
            System.out.println("balance: "+ balance);
        }
    }
    // synchronized void increment() {//when only make increment-method atomic, printing appear in unexpected order
    //     balance++;
    // }

    public static void main(String[] args) {
        LostUpdate job = new LostUpdate();
        Thread a = new Thread(job);//(without the "synchronized")this code represents the race condition
        Thread b = new Thread(job);//output is unexpectable.
        a.start();
        b.start();
    }

}