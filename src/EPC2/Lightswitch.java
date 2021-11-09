package EPC2;

import java.util.concurrent.Semaphore;

public class Lightswitch {

    private int counter;
    private final Semaphore mutex;

    public Lightswitch() {
        this.counter = 0;
        this.mutex = new Semaphore(1);
    }

    public void lock(Semaphore semaphore) {
        try {
            mutex.acquire();
        } catch (InterruptedException ignored) {}
        counter++;
        if (counter == 1) {
            try {
                semaphore.acquire();
            } catch (InterruptedException ignored) {}
        }
        mutex.release();
    }

    public void unlock(Semaphore semaphore) {
        try {
            mutex.acquire();
        } catch (InterruptedException ignored) {}
        counter--;
        if (counter == 0) {
            semaphore.release();
        }
        mutex.release();
    }
}
