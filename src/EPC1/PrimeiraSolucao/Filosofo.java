package EPC1.PrimeiraSolucao;

/*
   Implementacao do Jantar dos Filosofos com Semaforo
   PUCRS - Escola Politecnica
   Prof: Fernando Dotti
*/
import java.util.concurrent.Semaphore;

/*
 	Solucao 1 - Tanenbaum’s solution.
*/

// ============= Filosofo ==============
class Filosofo extends Thread {

    private final int i;
    private final String[] state;
    private final Semaphore[] sem;
    private final Semaphore mutex;

    private String espaco;

    public Filosofo(int i, String[] state, Semaphore[] sem, Semaphore mutex) {
        this.i = i;
        this.state = state;
        this.sem = sem;
        this.mutex = mutex;
        this.espaco = "  ";
        for (int k = 0; k < i; k++) {
            this.espaco = espaco + "                       ";
        }
    }

    public void run() {
        while (true) {
            try {
                getFork();
            } catch (InterruptedException ignored) {}
            try {
                putFork();
            } catch (InterruptedException ignored) {}
        }
    }

    private void getFork() throws InterruptedException {
        mutex.acquire();
        state[i] = "hungry";
        test(i);
        mutex.release();
        sem[i].acquire();
    }

    private void putFork() throws InterruptedException {
        mutex.acquire();
        state[i] = "thinking";
        System.out.println(espaco + i + ": Pegou um.");
        test(right(i));
        test(left(i));
        mutex.release();
    }

    private void test(int i) {
        if(
                state[i].equals("hungry") &&
                !state[left(i)].equals("eating") &&
                !state[right(i)].equals("eating")
        ) {
            state[i] = "eating";
            System.out.println(espaco + i + ": Pegou dois, está comendo.");
            sem[i].release();
        }
    }

    private int right(int i) {
        if (i >= sem.length - 1) {
            return 0;
        }
        return i + 1;
    }
    private int left(int i) {
        if (i > 0) {
            return i - 1;
        }
        return sem.length - 1;
    }

}

