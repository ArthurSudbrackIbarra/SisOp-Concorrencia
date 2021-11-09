package EPC1.SegundaSolucao;

/*
   Implementacao do Jantar dos Filosofos com Semaforo
   PUCRS - Escola Politecnica
   Prof: Fernando Dotti
*/
import java.util.concurrent.Semaphore;

/*
 	Solucao 2 - Pegar 2 garfos ao mesmo tempo.
*/

// ============= Filosofo ==============
class Filosofo extends Thread {

    private int i;
    private final boolean[] garfos;
    private final int indexGarfo1;
    private final int indexGarfo2;
    private final Semaphore mutex;
    private String espaco;

    public Filosofo(int i, boolean[] garfos, int indexGarfo1, int indexGarfo2, Semaphore mutex) {
        this.i = i;
        this.garfos = garfos;
        this.indexGarfo1 = indexGarfo1;
        this.indexGarfo2 = indexGarfo2;
        this.mutex = mutex;

        this.espaco = "  ";
        for (int k = 0; k < i; k++) {
            this.espaco = espaco + "                       ";
        }
    }

    public void run() {
        while (true) {
            // Pensa.
            System.out.println(espaco + i + ": Pensa ");
            // Tenta pegar 2 garfos.
            try {
                // Sessão crítica.
                mutex.acquire();
                if (garfos[indexGarfo1] && garfos[indexGarfo2]) {
                    garfos[indexGarfo1] = false;
                    System.out.println(espaco + i + ": Pegou um ");
                    garfos[indexGarfo2] = false;
                    System.out.println(espaco + i + ": Pegou dois, está comendo.");
                    mutex.release();
                    System.out.println(espaco + i + ": Terminou de comer");
                    // Solta garfos.
                    mutex.acquire();
                    garfos[indexGarfo1] = true;
                    garfos[indexGarfo2] = true;
                }
                // Libera sessão crítica.
                mutex.release();
            } catch (InterruptedException ignored) {}
        }
    }

}
