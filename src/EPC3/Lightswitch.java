package EPC3;

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
                System.out.println("Banheiro agora trancado para somente 1 genero.");
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
            System.out.println("Banheiro agora livre para qualquer genero.");
        }
        mutex.release();
    }
}
