package EPC1.PrimeiraSolucao;

import java.util.concurrent.Semaphore;

public class JantaFilosofos {

    public static void main(String[] args) {

        int fil = 5;

        String[] state = new String[fil];
        Semaphore[] sem = new Semaphore[fil];
        Semaphore mutex = new Semaphore(1);

        for (int i = 0; i < fil; i++) {
            state[i] = "thinking";
        }

        for (int i = 0; i < fil; i++) {
            sem[i] = new Semaphore(0);
        }

        for (int i = 0; i < fil; i++) {
            new Filosofo(i, state, sem, mutex).start();
        }
    }
}
