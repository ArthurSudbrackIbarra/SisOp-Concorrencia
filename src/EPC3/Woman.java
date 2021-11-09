package EPC3;

import java.util.concurrent.Semaphore;

public class Woman extends Thread {

    private final int id;
    private final Lightswitch lightswitch;
    private final Semaphore mutex;
    private final Semaphore empty;

    public Woman(int id, Lightswitch lightswitch, Semaphore mutex, Semaphore empty) {
        this.id = id;
        this.lightswitch = lightswitch;
        this.mutex = mutex;
        this.empty = empty;
    }

    public void run() {
        while (true) {
            lightswitch.lock(empty);
            try {
                mutex.acquire();
            } catch (InterruptedException ignored) {}
            System.out.println("Mulher " + id + " entrou no banheiro.");
            mutex.release();
            lightswitch.unlock(empty);
        }
    }

}
