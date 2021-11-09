package EPC2;

import java.util.concurrent.Semaphore;

public class NoStarvation {

    static Lightswitch readSwitch = new Lightswitch();
    static Semaphore roomEmpty = new Semaphore(1);
    static Semaphore turnstile = new Semaphore(1);

    static class Read implements Runnable {
        @Override
        public void run() {
            try {
                turnstile.acquire();
                turnstile.release();
                readSwitch.lock(roomEmpty);

                // Critical writing section.
                System.out.println("Thread " + Thread.currentThread().getName() + " está LENDO");
                Thread.sleep(1500);
                System.out.println("Thread " + Thread.currentThread().getName() + " terminou de LER");

                readSwitch.unlock(roomEmpty);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Write implements Runnable {
        @Override
        public void run() {
            try {
                turnstile.acquire();
                roomEmpty.acquire();

                // Critical writing section.
                System.out.println("Thread " + Thread.currentThread().getName() + " está ESCREVENDO");
                Thread.sleep(2500);
                System.out.println("Thread " + Thread.currentThread().getName() + " terminou de ESCREVER");

                turnstile.release();
                roomEmpty.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Read read = new Read();
        Write write = new Write();

        Thread t1 = new Thread(read);
        t1.setName("thread1");

        Thread t2 = new Thread(read);
        t2.setName("thread2");

        Thread t3 = new Thread(write);
        t3.setName("thread3");

        Thread t4 = new Thread(read);
        t4.setName("thread4");

        t1.start();
        t3.start();
        t4.start();
        t2.start();
    }
}
