package EPC1.PrimeiraSolucao;

/*
   Implementacao do Jantar dos Filosofos com Semaforo
   PUCRS - Escola Politecnica
   Prof: Fernando Dotti
*/
import java.util.concurrent.Semaphore;

/*
	Solucao 1 - Retirar 1 filosofo.
*/

// ============= Filosofo ==============
class Filosofo extends Thread {

    private int i;
    private Semaphore g1, g2;
    private String espaco;

    public Filosofo(int _i, Semaphore _g1, Semaphore _g2) {
        i = _i;   g1 = _g1;    g2 = _g2;
        espaco = "  ";
        for (int k = 0; k < i; k++) {
            espaco = espaco + "                       ";
        }
    }

    public void run() {
        while (true) {
            // pensa
            System.out.println(espaco+ i + ": Pensa ");

            // pega um garfo
            try{
                g1.acquire();
            } catch(InterruptedException ie) {}
            System.out.println(espaco+ i + ": Pegou um ");

            // pega outro garfo
            try {
                g2.acquire();
            } catch(InterruptedException ie) {}

            System.out.println(espaco+ i + ": Pegou dois, come ");
            // come
            // solta garfos
            g1.release();
            g2.release();
        }
    }

}

class JantaFilosofos {

    public static void main(String[] args) {
        int FIL = 5;
        Semaphore garfo[] = new Semaphore[FIL];
        for (int i = 0; i < FIL; i++) {
            garfo[i]= new Semaphore(1);
        }
        for (int i = 0; i < FIL - 1; i++) {
            (new Filosofo(i,garfo[i],garfo[(i+1)%(FIL)])).start();
        }
    }

}
