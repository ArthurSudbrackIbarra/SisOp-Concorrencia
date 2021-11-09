package EPC1.ProblemaBarreira;

import java.util.concurrent.Semaphore;

public class Barrier {

    // Quantidade de células da matriz.
    private final int cellsCount;
    // Quantidade de threads esperando na barreira.
    private int count;

    // Semáforo mutex para alterar 'count'.
    private final Semaphore mutex;
    // Catraca 1.
    private final Semaphore turnstile1;
    // Catraca 2.
    private final Semaphore turnstile2;

    public Barrier(int cellsCount) {
        this.cellsCount = cellsCount;
        this.count = 0;
        this.mutex = new Semaphore(1);
        this.turnstile1 = new Semaphore(0);
        this.turnstile2 = new Semaphore(0);
    }

    // Fase de leitura.
    public void readPhase() {
        try {
            mutex.acquire();
        } catch (InterruptedException ignored) {}
        count++;
        if (count == cellsCount) {
            turnstile1.release(cellsCount);
        }
        mutex.release();
        try {
            turnstile1.acquire();
        } catch (InterruptedException ignored) {}
    }

    // Fase de escrita.
    public void writePhase() {
        try {
            mutex.acquire();
        } catch (InterruptedException ignored) {}
        count--;
        if (count == 0) {
            turnstile2.release(cellsCount);
        }
        mutex.release();
        try {
            turnstile2.acquire();
        } catch (InterruptedException ignored) {}
    }
}
