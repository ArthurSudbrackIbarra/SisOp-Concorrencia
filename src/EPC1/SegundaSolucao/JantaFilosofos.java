package EPC1.SegundaSolucao;

import java.util.concurrent.Semaphore;

public class JantaFilosofos {

    public static void main(String[] args) {

        int fil = 5;

        boolean[] garfos = new boolean[fil];

        for (int i = 0; i < fil; i++) {
            garfos[i] = true;
        }

        Semaphore mutex = new Semaphore(1);

        for (int i = 0; i < fil; i++) {
            new Filosofo(i, garfos, i, (i + 1) % fil, mutex).start();
        }
    }

}
